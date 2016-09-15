package Test;

import Model.Atom;
import View.FluxQueryBean;
import View.FluxResultBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-10 Statistic Operation (Average,Median,Standard Deviation, Median Absolute Deviation) of flux ratios by spectral category. Every operation
 * can optionally specify an aperture to filter risults
 */
public class REQ_FN_10_Flux_Ratio_Statistics {

    String[] apertures = {"NONE","LOW","CENTRAL","HIGH"};

    @Test
    public void getFluxRatioStatisticsAverage() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux ratio Average operation
        bean.setOperation("AVG");
        bean.setCategory("S1h");
        bean.setFluxNum(Atom.OIII52);
        bean.setFluxDen(Atom.NIII57);


        List<FluxResultBean> result;
        FluxResultBean operationResult;
        for(int i = 0; i < 4; i++){
            bean.setAperture(apertures[i]);
            result = bean.performQuery();
            operationResult = result.get(0);

            switch (apertures[i]){
                case "NONE":
                    assertEquals(operationResult.getFluxValue(),8.71838, 0.001);
                    break;
                case "LOW":
                    assertEquals(operationResult.getFluxValue(),7.8790, 0.001);
                    break;
                case "CENTRAL":
                    assertEquals(operationResult.getFluxValue(),9.61169, 0.001);
                    break;
                case "HIGH":
                    assertEquals(operationResult.getFluxValue(),8.72819, 0.001);
                    break;
            }

        }
    }

    @Test
    public void getFluxRatioStatisticsMedian() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux ratio Median operation
        bean.setOperation("MEDIAN");
        bean.setCategory("S1h");
        bean.setFluxNum(Atom.OIII52);
        bean.setFluxDen(Atom.NIII57);


        List<FluxResultBean> result;
        FluxResultBean operationResult;
        for(int i = 0; i < 4; i++){
            bean.setAperture(apertures[i]);
            result = bean.performQuery();
            operationResult = result.get(0);


            switch (apertures[i]){
                case "NONE":
                    assertEquals(operationResult.getFluxValue(),3.87144, 0.001);
                    break;
                case "LOW":
                    assertEquals(operationResult.getFluxValue(),3.72978, 0.001);
                    break;
                case "CENTRAL":
                    assertEquals(operationResult.getFluxValue(),4.74346, 0.001);
                    break;
                case "HIGH":
                    assertEquals(operationResult.getFluxValue(),3.84521, 0.001);
                    break;
            }

        }
    }

    @Test
    public void getFluxRatioStatisticsStdDev() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux ratio Standard Deviation operation
        bean.setOperation("STD_DEV");
        bean.setCategory("S1h");
        bean.setFluxNum(Atom.OIII52);
        bean.setFluxDen(Atom.NIII57);


        List<FluxResultBean> result;
        FluxResultBean operationResult;
        for(int i = 0; i < 4; i++){
            bean.setAperture(apertures[i]);
            result = bean.performQuery();
            operationResult = result.get(0);


            switch (apertures[i]){
                case "NONE":
                    assertEquals(operationResult.getFluxValue(),13.45500, 0.001);
                    break;
                case "LOW":
                    assertEquals(operationResult.getFluxValue(),12.05130, 0.001);
                    break;
                case "CENTRAL":
                    assertEquals(operationResult.getFluxValue(),14.19775, 0.001);
                    break;
                case "HIGH":
                    assertEquals(operationResult.getFluxValue(),14.14410, 0.001);
                    break;
            }

        }
    }

    @Test
    public void getFluxRatioStatisticsMAD() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux ratio Median Absolute Deviation operation
        bean.setOperation("MED_ABS_DEV");
        bean.setCategory("S1h");
        bean.setFluxNum(Atom.OIII52);
        bean.setFluxDen(Atom.NIII57);


        List<FluxResultBean> result;
        FluxResultBean operationResult;
        for(int i = 0; i < 4; i++){
            bean.setAperture(apertures[i]);
            result = bean.performQuery();
            operationResult = result.get(0);

            switch (apertures[i]){
                case "NONE":
                    assertEquals(operationResult.getFluxValue(),8.71211, 0.001);
                    break;
                case "LOW":
                    assertEquals(operationResult.getFluxValue(),7.80322, 0.001);
                    break;
                case "CENTRAL":
                    assertEquals(operationResult.getFluxValue(),9.19304, 0.001);
                    break;
                case "HIGH":
                    assertEquals(operationResult.getFluxValue(),9.15830, 0.001);
                    break;
            }

        }
    }

}