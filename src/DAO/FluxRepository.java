package DAO;

import Model.Atom;
import Model.Flux;
import Model.FluxType;
import View.FluxResultBean;
import utils.EntityManagerSingleton;
import utils.JDBCConnectionManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all interactions with database and flux table
 */
public class FluxRepository {


    private static long ID = 1;
    /**
     * persist a flux
     * @param f
     */
    public static void save(Flux f) {

        EntityManager em = EntityManagerSingleton.getEntityManager();


        if(fluxAlreadyInDB(f)){
            return;
        } else {
            //insertFlux(f);
            em.persist(f);

        }

    }

    private static void insertFlux(Flux f) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;

        ResultSet rs = null;


        String query = "INSERT INTO flux( value, aperture, atom, error, fluxtype, upperlimit, galaxy_name,measureid)" +
                " VALUES (?,?,?,?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(query);
            statement.setDouble(1,f.getValue());
            statement.setString(2,f.getAperture().toString());
            statement.setString(3,f.getAtom().toString());
            statement.setFloat(4,f.getError());
            statement.setString(5,f.getFluxType().toString());
            statement.setString(6, String.valueOf(f.getUpperLimit()));
            statement.setString(7,f.getGalaxy().getName());
            statement.setLong(8, ID);

            ID++;


            statement.execute();


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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static boolean fluxAlreadyInDB(Flux f) {
        EntityManager eM = EntityManagerSingleton.getEntityManager();

        try {
            eM.createQuery("SELECT f FROM Flux f WHERE f.value = :v AND f.aperture = :a AND f.galaxy = :g AND f.fluxType = :type", Flux.class)
                    .setParameter("v", f.getValue())
                    .setParameter("a", f.getAperture())
                    .setParameter("g", f.getGalaxy())
                    .setParameter("type",f.getFluxType())
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException e){
            return false;
        }

        System.out.println("Flux Already Present");
        return true;
    }

    /**
     * given a galaxy and atom , find the related flux (if present)
     * @param galaxyName
     * @param atom
     * @return result flux
     * @throws NoResultException
     */
    public static Flux findLineFluxByGalaxy(String galaxyName, Atom atom) throws NoResultException {

        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        Flux f;
        try {
            f = entityManager.createQuery("SELECT DISTINCT f FROM Flux f WHERE f.galaxy.name = :galaxyName AND f.fluxType = :fType AND f.atom = :atom", Flux.class)
                    .setParameter("galaxyName", galaxyName)
                    .setParameter("fType", FluxType.LINE)
                    .setParameter("atom", atom)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e){
            throw e;
        }


        return f;

    }

    /**
     * given a galaxy and two fluxes,find the ratio between the two values
     * @param galaxyName
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @return list of results
     */
    public static List<FluxResultBean> findFluxRatio(String galaxyName, Atom fluxNum, Atom fluxDen) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();


        String query = "SELECT  f1.value/f2.value AS ratio, f1.upperLimit AS UL1, f2.upperlimit AS UL2, f1.aperture AS ap, f1.galaxy_name AS gName FROM ( SELECT DISTINCT galaxy_name,value,upperlimit,aperture FROM flux JOIN galaxy" +
                "                ON flux.galaxy_name = galaxy.name WHERE galaxy_name = ? AND fluxtype = 'LINE' and atom = ?) AS f1  JOIN" +
                "                (SELECT DISTINCT galaxy_name,value,upperlimit,aperture FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name WHERE galaxy_name = ? AND fluxtype ='LINE' AND atom = ?) AS f2" +
                "                ON f1.galaxy_name = f2.galaxy_name WHERE  f1.value <> -1 AND f2.value <> -1 AND f1.aperture = f2.aperture";
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,galaxyName);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,galaxyName);
            statement.setString(4,fluxDen.toString());

            rs = statement.executeQuery();
            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setGalaxyName(rs.getString("gName"));
                bean.setFluxValue(rs.getDouble("ratio"));
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture(rs.getString("ap"));
                if(rs.getString("UL1").charAt(0) == '<'){
                    bean.setNumUpperLimit(true);
                }else {
                    bean.setNumUpperLimit(false);
                }

                if(rs.getString("UL2").charAt(0) == '<'){
                    bean.setDenUpperLimit(true);
                } else {
                    bean.setDenUpperLimit(false);
                }

                result.add(bean);
            }

            return result;

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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * finds average flux ratio given a spectral category and two fluxes
     * @param category spectral category
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @return list of results
     */
    public static List<FluxResultBean> findAverageFluxRatioByCategory(String category, Atom fluxNum, Atom fluxDen) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT AVG(f1.value/f2.value) AS average FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("average"));
                bean.setStatisticsOperation("AVERAGE");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture("NOT SPECIFIED");

                result.add(bean);

            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * finds average flux ratio given a spectral category and two fluxes and a specific aperture size
     * @param category spectral classification
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @param aperture specific aperture
     * @return list of results
     */
    public static List<FluxResultBean> findAverageFluxRatioByCategoryAndAperture(String category, Atom fluxNum, Atom fluxDen, String aperture) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT AVG(f1.value/f2.value) AS average FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture AND f1.aperture = ? ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());
            statement.setString(5,aperture);

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("average"));
                bean.setStatisticsOperation("AVERAGE");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture(aperture);
                result.add(bean);
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * find median of fluxes ratio for a specific spectral category and two specific fluxes
     * @param category spectral category
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @return list of results
     */
    public static List<FluxResultBean> findMedianFluxRatioByCategory(String category, Atom fluxNum, Atom fluxDen) {

        int medianPosition = getMedianPositionFluxRatio(category,fluxNum,fluxDen);

        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT f1.value/f2.value AS median FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture ORDER BY median LIMIT 1 OFFSET ? ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());
            statement.setInt(5,medianPosition);

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("median"));
                bean.setStatisticsOperation("MEDIAN");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture("NOT DEFINED");

                result.add(bean);

            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * get the number of fluxes ratio given a specific category and two fluxes to be used in median calculation
     * @param category spectral category
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @return number of fluxes ratio
     */
    private static int getMedianPositionFluxRatio(String category, Atom fluxNum, Atom fluxDen) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        int result = 0;

        String query = "SELECT COUNT(f1.value/f2.value) / 2 AS mPos FROM" +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());

            rs = statement.executeQuery();

            while (rs.next()){
                result = rs.getInt("mPos");
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * find median of fluxes ratio for a specific spectral category and two specific fluxes and a specific aperture
     * @param category
     * @param fluxNum
     * @param fluxDen
     * @param aperture specific
     * @return
     */
    public static List<FluxResultBean> findMedianFluxRatioByCategoryAndAperture(String category, Atom fluxNum, Atom fluxDen, String aperture) {
        int medianPosition = getMedianPositionFluxRatioAndAperture(category,fluxNum,fluxDen,aperture);

        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT f1.value/f2.value AS median FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture AND f1.aperture = ? ORDER BY median LIMIT 1 OFFSET ? ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());
            statement.setString(5,aperture);
            statement.setInt(6,medianPosition);

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("median"));
                bean.setStatisticsOperation("MEDIAN");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture(aperture);

                result.add(bean);

            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * get the number of fluxes ratio given a specific category and two fluxes to be used in median calculation with specific aperture
     * @param category
     * @param fluxNum
     * @param fluxDen
     * @param aperture
     * @return number of fluxes ratio
     */
    private static int getMedianPositionFluxRatioAndAperture(String category, Atom fluxNum, Atom fluxDen, String aperture) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        int result = 0;

        String query = "SELECT COUNT(f1.value/f2.value) / 2 AS mPos FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture AND f1.aperture = ? ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());
            statement.setString(5,aperture);

            rs = statement.executeQuery();

            while (rs.next()){
                result = rs.getInt("mPos");
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * calculate standard deviation of fluxes ration given a specific category and two fluxes
     * @param category
     * @param fluxNum
     * @param fluxDen
     * @return list of results
     */
    public static List<FluxResultBean> findStdDevFluxRatioByCategory(String category, Atom fluxNum, Atom fluxDen) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT STDDEV(f1.value/f2.value) AS stdDev FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN" +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());


            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("stdDev"));
                bean.setStatisticsOperation("STANDARD DEVIATION");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture("NOT DEFINED");
                result.add(bean);
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * calculate standard deviation of fluxes ration given a specific category and two fluxes and specific aperture
     * @param category
     * @param fluxNum
     * @param fluxDen
     * @param aperture
     * @return list of results
     */
    public static List<FluxResultBean> findStdDevFluxRatioByCategoryAndAperture(String category, Atom fluxNum, Atom fluxDen, String aperture) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT STDDEV(f1.value/f2.value) AS stdDev FROM " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f1 " +
                "CROSS JOIN " +
                "(SELECT DISTINCT value,aperture,atom,spectralclassification FROM flux JOIN galaxy ON flux.galaxy_name = galaxy.name AND " +
                "spectralclassification = ? AND fluxtype='LINE' AND flux.value <>-1 AND atom = ? ) AS f2 " +
                "WHERE f1.aperture = f2.aperture AND f1.aperture = ? ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,category);
            statement.setString(2,fluxNum.toString());
            statement.setString(3,category);
            statement.setString(4,fluxDen.toString());
            statement.setString(5,aperture);

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setFluxValue(rs.getDouble("stdDev"));
                bean.setStatisticsOperation("STANDARD DEVIATION");
                // spectral classification; not galaxy name
                bean.setGalaxyName(category);
                bean.setAtom(fluxNum);
                bean.setDenAtom(fluxDen);
                bean.setAperture(aperture);
                result.add(bean);
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * given a specific flux and galaxy, calculate ratio between that line flux and its related continuous flux
     * @param galaxyName
     * @param fluxNum
     * @return list of results
     */
    public static List<FluxResultBean> findFluxRatioContinuous(String galaxyName, Atom fluxNum) {
        Connection conn = JDBCConnectionManager.getDBConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<FluxResultBean> result = new ArrayList<>();

        String query = "SELECT line.value/cont.value AS ratioCont FROM " +
                "(SELECT DISTINCT f1.value,f1.aperture FROM flux AS f1 WHERE f1.fluxtype = 'LINE' AND atom = ? AND galaxy_name = ? AND f1.value <> -1 ) AS line " +
                "CROSS JOIN " +
                "(SELECT DISTINCT f2.value,f2.aperture FROM flux AS f2 WHERE f2.fluxtype = 'CONTINUOUS' and atom = ? and galaxy_name = ? AND f2.value <> -1 ) AS cont " +
                "WHERE cont.aperture=line.aperture ";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,fluxNum.toString());
            statement.setString(2,galaxyName);
            statement.setString(3,fluxNum.toString());
            statement.setString(4,galaxyName);

            rs = statement.executeQuery();

            while (rs.next()){
                FluxResultBean bean = new FluxResultBean();
                bean.setGalaxyName(galaxyName);
                bean.setFluxValue(rs.getDouble("ratioCont"));
                bean.setAtom(fluxNum);

                result.add(bean);
            }


            return result;
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
            if(conn != null){
                try {
                    JDBCConnectionManager.closeDBConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
