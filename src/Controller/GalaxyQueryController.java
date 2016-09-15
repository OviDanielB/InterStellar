package Controller;

import DAO.GalaxyRepository;
import Model.Ascension;
import Model.Declination;
import Model.Galaxy;
import Model.SpacePosition;
import View.GalaxyQueryBean;
import View.GalaxyResultBean;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages all galaxy queries from GalaxyQueryBean; name query,position query,redshift query
 */
public class GalaxyQueryController {

    private static GalaxyQueryController instance;

    private GalaxyQueryController(){
    }

    /* singleton pattern*/
    public static GalaxyQueryController getInstance(){
        if(instance == null){
            instance = new GalaxyQueryController();
        }
        return instance;
    }


    /**
     * query galaxies by name;
     * @param galaxyName query
     * @return list of possible result (if no result, array is empty NOT NULL)
     */
    public List<GalaxyResultBean> getGalaxiesByName(String galaxyName) {


        return GalaxyRepository.findGalaxyFullDescriptionByName(galaxyName);
    }


    /**
     * given a specific position(Ascension,Declination) finds N galaxies from that point and orders them by distance
     * @param galaxyQueryBean query ; contains position,how many galaxies to select
     * @return list of result; list is empty if no results found (NOT NULL)
     */
    public List<GalaxyResultBean> getGalaxiesByPosition(GalaxyQueryBean galaxyQueryBean) {
        Ascension ascension = new Ascension();
        Declination declination = new Declination();
        SpacePosition position = new SpacePosition();

        int many = 0;

        try {
            ascension.setAscensionHour(parseIntTrim(galaxyQueryBean.getAscH()));
            ascension.setAscensionMin(parseIntTrim(galaxyQueryBean.getAscM()));
            ascension.setAscensionSec(parseFloatTrim(galaxyQueryBean.getAscS()));

            declination.setDeclinationSign(galaxyQueryBean.getDeclSign().trim().charAt(0));
            declination.setDeclinationDegrees(parseIntTrim(galaxyQueryBean.getDeclD()));
            declination.setDeclinationMin(parseIntTrim(galaxyQueryBean.getDeclM()));
            declination.setDeclinationSec(parseFloatTrim(galaxyQueryBean.getDeclS()));

            many = parseIntTrim(galaxyQueryBean.getMany());

        } catch (NumberFormatException e){
            return new ArrayList<>();
        }

        position.setAscension(ascension);
        position.setDeclination(declination);

        return GalaxyRepository.findInRange(position,many);
    }

    /**
     * trims and parses float
     * @param ascS
     * @return float parsed
     * @throws NumberFormatException
     */
    private float parseFloatTrim(String ascS) throws NumberFormatException {
        try {
            return Float.parseFloat(ascS.trim());
        } catch (NumberFormatException e){
            throw e;
        }
    }

    /**
     * trims and parses int
     * @param ascH
     * @return int parse
     * @throws NumberFormatException
     */
    private int parseIntTrim(String ascH) throws NumberFormatException {

        try {
            return Integer.parseInt(ascH.trim());
        } catch (NumberFormatException e){
            throw e;
        }
    }

    /**
     * galaxy REDSHIFT query operation; given a redshift value, an operation sign ('<=' or '>=') performs query finding first N galaxies
     * with less or greater redshift value, ordered by value
     * @param redshift value
     * @param redshiftOpSign operation sign
     * @param many N selected galaxies
     * @return list of results
     */
    public List<GalaxyResultBean> getGalaxiesByRedshift(String redshift, String redshiftOpSign, String many) {

        List<GalaxyResultBean> result = null;

        switch (redshiftOpSign){
            case "<=":
                try {
                    result = galaxyToGalaxyBean(GalaxyRepository.findGalaxiesByLessRedshift(parseFloatTrim(redshift), parseIntTrim(many)));
                } catch (NoResultException e){
                    return new ArrayList<>();
                }
                break;
            case ">=":
                try {
                    result = galaxyToGalaxyBean(GalaxyRepository.findGalaxiesByGreaterRedshift(parseFloatTrim(redshift), parseIntTrim(many)));
                } catch (NoResultException e){
                    return new ArrayList<>();
                }
                break;
            default:
                return new ArrayList<>();
        }

        return result;

    }

    /**
     * Given a galaxy list, transforms it into GalaxyResultBean in Redshift query
     * @param galaxiesByLessRedshift galaxies to transform
     * @return list of results
     */
    private List<GalaxyResultBean> galaxyToGalaxyBean(List<Galaxy> galaxiesByLessRedshift) {
        List<GalaxyResultBean> result = new ArrayList<>();
        for(Galaxy g: galaxiesByLessRedshift){
            GalaxyResultBean bean = new GalaxyResultBean();
            bean.setName(g.getName());
            bean.setRedshift(g.getDistance().getRedshift());
            result.add(bean);
        }

        return result;
    }
}
