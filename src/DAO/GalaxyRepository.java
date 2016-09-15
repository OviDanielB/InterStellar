package DAO;

import Model.Galaxy;
import Model.SpacePosition;
import View.GalaxyResultBean;
import utils.EntityManagerSingleton;
import utils.JDBCConnectionManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * manages interaction with db and Galaxy entity
 */
public class GalaxyRepository {


    /**
     * persist galaxy if not already present in db
     * @param galaxy
     */
    public static void save(Galaxy galaxy){

        EntityManager entityManager = EntityManagerSingleton.getEntityManager();


        if(entityManager.find(Galaxy.class,galaxy.getName()) != null){
            entityManager.merge(galaxy);
            return;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(galaxy);
        entityManager.getTransaction().commit();
    }

    /**
     * query galaxy by name (key)
     * @param name galaxy name
     * @return result galaxy
     */
    public static Galaxy findByName(String name){
        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        Galaxy galaxy = entityManager.find(Galaxy.class,name);

        return galaxy;
    }


    /**
     * given a galaxy name (or similar) returns galaxy info with distance,position,redshift, luminosity (average value) and metallicity
     * @param name galaxy name
     * @return list of galaxy bean used in view
     */
    public static List<GalaxyResultBean> findGalaxyFullDescriptionByName(String name){
        Connection connection = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet resultSet;

        // every galaxy could have at most 3 luminosity measures so it calculates average; average also needed (not necessary) in metallicity and metallicity error
        String query = "SELECT DISTINCT g.*, AVG(lum.value) AS lumAVG, AVG(met.value) AS metal, AVG(met.error) AS metalErr " +
                "FROM galaxy AS g JOIN luminosity AS lum ON g.name = lum.galaxy_name JOIN metallicity AS met " +
                "ON lum.galaxy_name = met.galaxy_name WHERE (g.name LIKE ? OR g.alternativename LIKE ?) AND lum.value <> -1  " +
                " GROUP BY name";

        System.out.println(query);


        List<GalaxyResultBean> result = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            // % used for LIKE statement to find any galaxy with 'name' in it
            statement.setString(1,"%" + name + "%");
            statement.setString(2,"%" + name + "%" );

            resultSet = statement.executeQuery();
            while (resultSet.next()){
                GalaxyResultBean bean = new GalaxyResultBean();
                bean.setName(resultSet.getString("name"));
                bean.setAscH(resultSet.getInt("ascensionhour"));
                bean.setAscM(resultSet.getInt("ascensionmin"));
                bean.setAscS(resultSet.getFloat("ascensionsec"));
                bean.setDecD(resultSet.getInt("declinationdegrees"));
                bean.setDecM(resultSet.getInt("declinationmin"));
                bean.setDecS(resultSet.getFloat("declinationsec"));
                bean.setDecSign(resultSet.getString("declinationsign").charAt(0));
                bean.setDistance(resultSet.getFloat("distancevalue"));
                bean.setRedshift(resultSet.getFloat("redshift"));
                bean.setLumAvg(resultSet.getFloat("lumAVG"));
                bean.setMetallicity(resultSet.getFloat("metal"));
                bean.setMetallicityError(resultSet.getFloat("metalErr"));

                result.add(bean);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    JDBCConnectionManager.closeDBConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * find  MANY galaxy in range from a central space POSITION; converts ascension and declination in degrees and calculates distance of every
     * galaxy from central point
     * @param position central space position
     * @param many number of galaxies from central position
     * @return list of galaxy bean to show in view
     */
    public static List<GalaxyResultBean> findInRange(SpacePosition position, int many){

        Connection connection = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet resultSet;

        /* trigonometric functions accept radiant values */
        String query = "SELECT name, ACOS( SIN( RADIANS( 15 * (? + ?/60 + ?/3600) ) ) * SIN( RADIANS( 15 * (ascensionhour + ascensionmin/60 + ascensionsec/3600) ) ) " +
                " + COS( RADIANS( 15 * (? + ?/60 + ?/3600) ) ) * COS( RADIANS(15 * (ascensionhour + ascensionmin/60 + ascensionsec/3600)) ) " +
                " * COS( RADIANS( (? + ?/60 + ?/3600) - (declinationdegrees + declinationmin/60 + declinationsec/3600) ) ) ) * 180 / 3.14 AS dist FROM galaxy ORDER BY dist";

        List<GalaxyResultBean> result = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,position.getAscension().getAscensionHour());
            statement.setInt(2,position.getAscension().getAscensionMin());
            statement.setFloat(3,position.getAscension().getAscensionSec());

            statement.setInt(4,position.getAscension().getAscensionHour());
            statement.setInt(5,position.getAscension().getAscensionMin());
            statement.setFloat(6,position.getAscension().getAscensionSec());

            /*  set declination sign in degrees */
            if(position.getDeclination().getDeclinationSign() == '-'){
                statement.setInt(7,position.getDeclination().getDeclinationDegrees() * -1);
            } else {
                statement.setInt(7,position.getDeclination().getDeclinationDegrees());
            }

            statement.setInt(8,position.getDeclination().getDeclinationMin());
            statement.setFloat(9,position.getDeclination().getDeclinationSec());


            resultSet = statement.executeQuery();

            int i = 0;
            // take only MANY galaxies
            while (resultSet.next() && i < many){
                GalaxyResultBean bean = new GalaxyResultBean();
                bean.setName(resultSet.getString("name"));
                bean.setDistance(resultSet.getFloat("dist"));
                result.add(bean);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    JDBCConnectionManager.closeDBConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * finds n galaxies with redshift <= specified values
     * @param redshift maximum redshift
     * @param many maximum number of galaxies
     * @return
     */
    public static List<Galaxy> findGalaxiesByLessRedshift(Float redshift, Integer many) throws NoResultException {
        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        try {
            return entityManager.createQuery("SELECT g FROM Galaxy g WHERE g.distance.redshift <= :redS ORDER BY g.distance.redshift ", Galaxy.class)
                    .setParameter("redS", redshift)
                    .setMaxResults(many)
                    .getResultList();
        }catch (NoResultException e){
            throw e;
        }
    }

    /**
     * finds n galaxies with redshift >= specified values
     * @param redshift minimum specified values
     * @param many maximum number of galaxies
     * @return list of matching galaxies
     */
    public static List<Galaxy> findGalaxiesByGreaterRedshift(Float redshift,Integer many) throws NoResultException {
        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        try {
            return entityManager.createQuery("SELECT g FROM Galaxy g WHERE g.distance.redshift >= :redS ORDER BY g.distance.redshift", Galaxy.class)
                    .setParameter("redS", redshift)
                    .setMaxResults(many)
                    .getResultList();
        }catch (NoResultException e){
            throw e;
        }
    }
}
