package Controller;

import DAO.FluxRepository;
import Model.Atom;
import Model.Flux;
import View.FluxResultBean;
import utils.FluxQueryOperation;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages all flux queries
 */
public class FluxQueryController {

    private static FluxQueryController instance;

    private FluxQueryController() {
    }

    /* singleton pattern*/
    public static FluxQueryController getInstance(){
        if(instance == null){
            instance = new FluxQueryController();
        }
        return instance;
    }


    /**
     * given a galaxy name, returns result matching the query
     * @param galaxyName query name
     * @param atoms flux atom
     * @return results
     */
    public List<FluxResultBean> getFluxByGalaxyName(String galaxyName, List<Atom> atoms) {
        List<FluxResultBean> result = new ArrayList<>();
        Flux f = null;
        for(Atom a : atoms) {
           try{
               f = FluxRepository.findLineFluxByGalaxy(galaxyName,a);
           } catch (NoResultException e){
               e.printStackTrace();
           }
           if(f != null) {
               result.add(converToFluxBeanNameQuery(f));
           }
        }

        return result;
    }

    /**
     * converts Flux class to FluxResult Bean
     * @param f
     * @return
     */
    private FluxResultBean converToFluxBeanNameQuery(Flux f) {
        FluxResultBean bean = new FluxResultBean();
        bean.setGalaxyName(f.getGalaxy().getName());
        bean.setAtom(f.getAtom());
        bean.setFluxValue(f.getValue());
        bean.setError(f.getError());

        boolean upperLimit = (f.getUpperLimit() == '<');
        bean.setUpperLimit(upperLimit);
        return bean;
    }

    /**
     * fiven a galaxy and two fluxes (atoms) gets the ratio between them
     * @param galaxyName
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @return results
     */
    public List<FluxResultBean> getFluxRatio(String galaxyName, Atom fluxNum, Atom fluxDen) {
        List<FluxResultBean> result = new ArrayList<>();

        result = FluxRepository.findFluxRatio(galaxyName,fluxNum,fluxDen);

        return result;
    }

    /**
     * performs statistic operations (Average,Median,Std Dev, Median Abs Dev) on flux ratio given a specific spectral category
     * @param category spectral category
     * @param fluxNum numerator flux
     * @param fluxDen denominator flux
     * @param aperture aperture (optional)
     * @param operation avg,stdDev,etc
     * @return results
     */
    public List<FluxResultBean> getFluxRatioStatistics(String category, Atom fluxNum, Atom fluxDen, String aperture, FluxQueryOperation operation) {

        List<FluxResultBean> result = new ArrayList<>();

        boolean withAperture = !aperture.equals("NONE");
        switch (operation){
            case STAT_AVG:
                if(withAperture){
                    result = FluxRepository.findAverageFluxRatioByCategoryAndAperture(category,fluxNum,fluxDen,aperture);
                }else {
                    result = FluxRepository.findAverageFluxRatioByCategory(category,fluxNum,fluxDen);
                }
                break;
            case STAT_MEDIAN:
                if(withAperture){
                    result = FluxRepository.findMedianFluxRatioByCategoryAndAperture(category,fluxNum,fluxDen,aperture);
                }else {
                    result = FluxRepository.findMedianFluxRatioByCategory(category,fluxNum,fluxDen);
                }
                break;

            case STAT_STD_DEV:
                if(withAperture){
                    result = FluxRepository.findStdDevFluxRatioByCategoryAndAperture(category,fluxNum,fluxDen,aperture);
                }else {
                    result = FluxRepository.findStdDevFluxRatioByCategory(category,fluxNum,fluxDen);
                }
                break;
            /*
             * https://it.wikipedia.org/wiki/Deviazione_media_assoluta
             * see link for approximation
             * MAD = 0.6475 * STDDEV
             */
            case STAT_MED_ABS_DEV:
                if(withAperture){
                    result = getMedianAbsDevByCategoryAndAperture(category,fluxNum,fluxDen,aperture);
                }else {
                    result = getMedianAbsDevByCategory(category,fluxNum,fluxDen);
                }
                break;
        }

        return result;
    }

    /**
     * approximates MAD from STDDEV for flux ratio
     * @param category spectral category
     * @param fluxNum
     * @param fluxDen
     * @return
     */
    private List<FluxResultBean> getMedianAbsDevByCategory(String category, Atom fluxNum, Atom fluxDen) {
        List<FluxResultBean> result = FluxRepository.findStdDevFluxRatioByCategory(category,fluxNum,fluxDen);
        FluxResultBean STDDEVbean = result.get(0);

        double MADvalue = 0.6475 * STDDEVbean.getFluxValue();

        STDDEVbean.setFluxValue(MADvalue);
        STDDEVbean.setStatisticsOperation("MEDIAN ABSOLUTE DEVIATION");


        return result;
    }

    /**
     * approximates MAD from STDDEV for flux ratio with aperture specified
     * @param category
     * @param fluxNum
     * @param fluxDen
     * @param aperture
     * @return
     */
    private List<FluxResultBean> getMedianAbsDevByCategoryAndAperture(String category, Atom fluxNum, Atom fluxDen, String aperture) {

        List<FluxResultBean> result = FluxRepository.findStdDevFluxRatioByCategoryAndAperture(category,fluxNum,fluxDen,aperture);
        FluxResultBean STDDEVbean = result.get(0);

        double MADvalue = 0.6475 * STDDEVbean.getFluxValue();

        STDDEVbean.setFluxValue(MADvalue);
        STDDEVbean.setStatisticsOperation("MEDIAN ABSOLUTE DEVIATION");


        return result;
    }

    /**
     * given a flux,calculate the ratio between it and its continuous value
     * @param galaxyName
     * @param fluxNum
     * @return
     */
    public List<FluxResultBean> getFluxRatioCont(String galaxyName, Atom fluxNum) {
        return FluxRepository.findFluxRatioContinuous(galaxyName,fluxNum);
    }
}
