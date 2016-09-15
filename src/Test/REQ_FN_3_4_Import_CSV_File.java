package Test;

import Controller.CSVController;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * IMPORTING CSV File Test
 */
public class REQ_FN_3_4_Import_CSV_File {
    @Test
    public void processCSVDataFile() throws Exception {

        String filePath = "src/res/";

        String SAMPLE_NAME = "MRTable3_sample.csv";
        String FLUX_NAME = "MRTable4_flux.csv";
        String CONT_NAME = "MRTable6_cont.csv";
        String IRS_NAME = "MRTable8_irs.csv";
        String APER_NAME = "MRTable11_C_3x3_5x5_flux.csv";

        CSVController controller = CSVController.getInstance();

        File fileSample = new File(filePath + SAMPLE_NAME);
        File fileFlux = new File(filePath + FLUX_NAME);
        File fileCont = new File(filePath + CONT_NAME);
        File fileIRS = new File(filePath + IRS_NAME);
        File fileAper = new File(filePath + APER_NAME);

        assertTrue(controller.processCSVDataFile(fileSample));
        assertTrue(controller.processCSVDataFile(fileFlux));
        assertTrue(controller.processCSVDataFile(fileCont));
        assertTrue(controller.processCSVDataFile(fileIRS));
        assertTrue(controller.processCSVDataFile(fileAper));

    }

}