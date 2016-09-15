package Controller;

import DAO.FluxRepository;
import DAO.GalaxyRepository;
import DAO.LuminosityRepository;
import DAO.MetallicityRepository;
import Model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import utils.CSVFileType;
import utils.fileHeaders.*;

import java.io.*;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that manages all csv files interaction; given a csv data file,makes it parsable (removes header),process every line of it and
 * depending on the file type, inserts info into database
 */
public class CSVController {

    private static CSVController instance;
    private static String TMP_DIR = "/tmp/";

    private static String SAMPLE_NAME = "MRTable3_sample.csv";
    private static String FLUX_NAME = "MRTable4_flux.csv";
    private static String CONT_NAME = "MRTable6_cont.csv";
    private static String IRS_NAME = "MRTable8_irs.csv";
    private static String APER_NAME = "MRTable11_C_3x3_5x5_flux.csv";

    private CSVController() {
    }

    /* singleton pattern*/
    public static CSVController getInstance(){
        if(instance == null){
            instance = new CSVController();
        }
        return instance;
    }

    /**
     * takes a csv file and processes it; first creates a new file only with useful information (no header or bibliography),
     * then for each row inserts in db records according to file type (SAMPLE,FLUX, etc)
     * @param file input file
     * @return true if operation completed successfully
     */
    public boolean processCSVDataFile(File file){

        try {
            checkFileNameValidity(file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        CSVDataFile parsableFile = createParsableCSVFile(file);
        System.out.println(parsableFile.getFile().getAbsolutePath());

        try {
            saveParsableFileInDB(parsableFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;


    }

    /**
     * checks if it's a valid file name
     * @param name name of file
     * @throws Exception
     */
    private void checkFileNameValidity(String name) throws Exception {
        ArrayList<String> validNames = new ArrayList<>();
        validNames.add(SAMPLE_NAME);
        validNames.add(FLUX_NAME);
        validNames.add(CONT_NAME);
        validNames.add(IRS_NAME);
        validNames.add(APER_NAME);

        for(String validName : validNames){
            if(validName.equals(name)){
                return;
            }
        }

        throw new Exception();
    }

    /**
     * according to file format, chooses corrisponding header to get values after;
     * loops for every row in the file
     * @param parsableFile file already processed and ready to insert
     * @throws IOException
     */
    private void saveParsableFileInDB(CSVDataFile parsableFile) throws IOException {

        Reader reader = new FileReader(parsableFile.getFile());

        Iterable<CSVRecord> records = null;

        switch (parsableFile.getType()){
            case SAMPLE:
                records = CSVFormat.DEFAULT
                        .withDelimiter(CSVDataFile.getDelimiter())
                        .withHeader(SampleHeader.class)
                        .parse(reader);
                break;
            case CONT:
                records = CSVFormat.DEFAULT
                        .withDelimiter(CSVDataFile.getDelimiter())
                        .withHeader(ContHeader.class)
                        .parse(reader);
                break;
            case IRS:
                records = CSVFormat.DEFAULT
                        .withDelimiter(CSVDataFile.getDelimiter())
                        .withHeader(IRSHeader.class)
                        .parse(reader);
                break;
            case APER_FLUX:
                records = CSVFormat.DEFAULT
                        .withDelimiter(CSVDataFile.getDelimiter())
                        .withHeader(FluxHeader.class)
                        .parse(reader);
                break;
            case FLUX:
                records = CSVFormat.DEFAULT
                        .withDelimiter(CSVDataFile.getDelimiter())
                        .withHeader(FluxHeader.class)
                        .parse(reader);
                break;


        }

        CSVFileType type = parsableFile.getType();

        for(CSVRecord record : records){
            persistRecord(record,type);

        }

    }

    /**
     * depending on file type, inserts records in DB
     * @param record a line of the csv file
     * @param type file type (FLUX,IRS,etc)
     */
    private void persistRecord(CSVRecord record, CSVFileType type) {
        
        switch (type){
            case SAMPLE:
                persistGalaxyInfo(record);
                break;
            case CONT:
                persistContFlux(record);
                break;
            case IRS:
                persistIRSFlux(record);
                break;
            case FLUX:
                persistFlux(record);
                break;
            case APER_FLUX:
                persistFlux(record);
                break;
        }
    }

    /**
     * creates a new galaxy and fills with info in every row of SAMPLE file type
     * @param record a SAMPLE csv line with galaxy info
     */
    private void persistGalaxyInfo(CSVRecord record){

        Ascension ascension = new Ascension();
        ascension.setAscensionHour(parseIntCorrectly(record.get(SampleHeader.ASC_H)));
        ascension.setAscensionMin(parseIntCorrectly(record.get(SampleHeader.ASC_M)));
        ascension.setAscensionSec(parseFloatCorrectly(record.get(SampleHeader.ASC_S)));

        Declination declination = new Declination();
        declination.setDeclinationSign(record.get(SampleHeader.DEC_SIGN).charAt(0));
        declination.setDeclinationDegrees(parseIntCorrectly(record.get(SampleHeader.DEC_DEGR)));
        declination.setDeclinationMin(parseIntCorrectly(record.get(SampleHeader.DEC_M)));
        declination.setDeclinationSec(parseFloatCorrectly(record.get(SampleHeader.DEC_S)));

        SpacePosition position = new SpacePosition();
        position.setAscension(ascension);
        position.setDeclination(declination);

        Distance distance = new Distance();
        distance.setDistanceValue(parseFloatCorrectly(record.get(SampleHeader.DIST)));
        distance.setRedshift(parseFloatCorrectly(record.get(SampleHeader.REDSHIFT)));

        Galaxy galaxy = new Galaxy();
        galaxy.setName(record.get(SampleHeader.NAME).trim());
        galaxy.setAlternativeName(record.get(SampleHeader.ALT_NAME).trim());
        galaxy.setDistance(distance);
        galaxy.setSpacePosition(position);
        galaxy.setSpectralClassification(record.get(SampleHeader.SPEC_CLASS).trim());

        // persist galaxy
        GalaxyRepository.save(galaxy);

        /*
            for each galaxy persists 3 luminosity measurements with 3 different atoms
         */
        persistLuminosity(record,Atom.NeV14,galaxy);
        persistLuminosity(record,Atom.NeV24,galaxy);
        persistLuminosity(record,Atom.OIV25,galaxy);

        persistMetallicity(record,galaxy);


    }

    /**
     * insert from a csv file line (SAMPLE) metallicity value and related galaxy
     * @param record file line
     * @param galaxy galaxy to refer to
     */
    private void persistMetallicity(CSVRecord record,Galaxy galaxy) {
        Metallicity metallicity = new Metallicity();
        metallicity.setGalaxy(galaxy);
        metallicity.setValue(parseDoubleCorrectly(record.get(SampleHeader.METALLICITY)));
        metallicity.setError(parseFloatCorrectly(record.get(SampleHeader.ERR_METAL)));

        MetallicityRepository.save(metallicity);
    }

    /**
     * persists luminosity records from csv file rows; creates a luminosity based on atom,referenced galaxy
     * @param record SAMPLE csv file type
     * @param atom measure atom
     * @param galaxy referenced galaxy
     */
    private void persistLuminosity(CSVRecord record,Atom atom,Galaxy galaxy) {

        Luminosity lum = new Luminosity();
        lum.setAtom(atom);
        switch (atom){
            case NeV14:
                lum.setUpperLimit(record.get(SampleHeader.LIM_LUM_NEV1).charAt(0));
                lum.setValue(parseDoubleCorrectly(record.get(SampleHeader.LUM_NEV1)));
                break;
            case NeV24:
                lum.setUpperLimit(record.get(SampleHeader.LIM_LUM_NEV2).charAt(0));
                lum.setValue(parseDoubleCorrectly(record.get(SampleHeader.LUM_NEV2)));
                break;
            case OIV25:
                lum.setUpperLimit(record.get(SampleHeader.LIM_LUM_OIV).charAt(0));
                lum.setValue(parseDoubleCorrectly(record.get(SampleHeader.LUM_OIV)));
                break;
        }

        lum.setGalaxy(galaxy);

        LuminosityRepository.save(lum);


    }

    /**
     * given a string, removes white spaces at the beginning and end of it and returns a double
     * @param s string to parse
     * @return double parsed (-1 if empty string)
     */
    private double parseDoubleCorrectly(String s){
        String newS = s.trim();
        if(newS.length() == 0){
            return -1;
        }

        return Double.parseDouble(newS);
    }

    /**
     * given a string, removes white spaces at the beginning and end of it and returns a float
     * @param s string to parse
     * @return float parsed (-1 if empty string)
     */
    private float parseFloatCorrectly(String s){

        String newS = s.trim();
        if(newS.length() == 0){
            return -1;
        }
        return Float.parseFloat(newS);
    }

    /**
     * given a string, removes white spaces at the beginning and end of it and returns an integer
     * @param string string to parse
     * @return integer parsed (-1 if empty string)
     */
    private int parseIntCorrectly(String string){

        String newS = string.trim();
        if(newS.length() == 0){
            return -1;
        }
        return Integer.parseInt(newS);
    }

    /**
     * persists a continuous flux for every csv file row of CONTINUOUS file type
     * @param record csv line
     */
    private void persistContFlux(CSVRecord record){

        Galaxy galaxy = returnGalaxyIfPresent(record.get(ContHeader.GALAXY_NAME).trim());

        List<Flux> fluxes = new ArrayList<>();

        // same for every row
        Aperture aperture = getApertureByString(record.get(ContHeader.APERTURE));

        List<Atom> atoms = new ArrayList<>();
        atoms.add(Atom.OIII52);
        atoms.add(Atom.NIII57);
        atoms.add(Atom.OI63);
        atoms.add(Atom.OIII88);
        atoms.add(Atom.NII122);
        atoms.add(Atom.OI145);
        atoms.add(Atom.CII158);


        /* for every atom inserts corrisponding flux */
        for(Atom a : atoms){
            Flux flux = new Flux();
            flux.setGalaxy(galaxy);
            flux.setFluxType(FluxType.CONTINUOUS);
            flux.setAtom(a);
            //the only 2 atoms without upperLimit
            if(a == Atom.OIII52 || a == Atom.NIII57) {
                flux.setUpperLimit('!');
            } else {
                flux.setUpperLimit(record.get(getLimitHeaderByAtomCon(a)).charAt(0));
            }
            flux.setValue(parseDoubleCorrectly(record.get(getValueHeaderByAtomCon(a))));
            flux.setError(parseFloatCorrectly(record.get(getErrorHeaderByAtomCon(a))));
            flux.setAperture(aperture);

            // persist
            FluxRepository.save(flux);
        }

    }

    private ContHeader getErrorHeaderByAtomCon(Atom a) {
        String s;
        for(ContHeader h : ContHeader.values()){
            s=h.toString();
            if(s.contains("ERR") && s.contains(a.toString())){
                return h;
            }
        }
        return null;
    }

    private ContHeader getValueHeaderByAtomCon(Atom a) {
        String s;
        for(ContHeader h : ContHeader.values()){
            s=h.toString();
            if(s.contains("CFLUX") && s.contains(a.toString())){
                return h;
            }
        }
        return null;
    }

    private ContHeader getLimitHeaderByAtomCon(Atom a) {
        String s;
        for(ContHeader h : ContHeader.values()){
            s=h.toString();
            if(s.contains("LIM") && s.contains(a.toString())){
                return h;
            }
        }
        return null;
    }

    /**
     * searches the DB to find if galaxy is prensent, else saves it;
     * fluxes need a galaxy to reference to so it has to be in the db
     * @param s galaxy name (key)
     * @return galaxy in DB or inserted
     */
    private Galaxy returnGalaxyIfPresent(String s) {
        Galaxy galaxy = GalaxyRepository.findByName(s);

        if(galaxy == null){
            galaxy = new Galaxy();
            galaxy.setName(s);
            GalaxyRepository.save(galaxy);
        }

        return galaxy;
    }

    /**
     * parses string s and return corrisponding Aperture type
     * @param s string to parse
     * @return aperture
     */
    private Aperture getApertureByString(String s) {

        if(s.contains(Aperture.LOW.getAperture())){
            return Aperture.LOW;
        } else if(s.contains(Aperture.CENTRAL.getAperture())){
            return Aperture.CENTRAL;
        } else if(s.contains(Aperture.HIGH.getAperture())){
            return Aperture.HIGH;
        } else {
            return Aperture.NOT_SPECIFIED;
        }
    }

    /**
     * for every line of IRS type file, inserts flux for every atom in the line, referenced to a specific galaxy
     * @param record line of csv file
     */
    private void persistIRSFlux(CSVRecord record) {

        Galaxy galaxy = returnGalaxyIfPresent(record.get(IRSHeader.GALAXY_NAME).trim());

        //Galaxy galaxy = GalaxyRepository.findByName(record.get(IRSHeader.GALAXY_NAME).trim());
        //the same for every row
        Aperture irsMode = getIRSMode(record.get(IRSHeader.MODE_IRS));

        List<Atom> atoms = new ArrayList<>();
        atoms.add(Atom.SIV10);
        atoms.add(Atom.NeII12);
        atoms.add(Atom.NeV14);
        atoms.add(Atom.NeIII15);
        atoms.add(Atom.SIII18);
        atoms.add(Atom.NeV24);
        atoms.add(Atom.OIV25);
        atoms.add(Atom.SIII33);
        atoms.add(Atom.SiII34);


        for(Atom a : atoms){
            Flux flux = new Flux();
            flux.setGalaxy(galaxy);
            flux.setFluxType(FluxType.LINE);
            flux.setAtom(a);
            flux.setUpperLimit(record.get(getLimitHeaderByAtomIRS(a)).charAt(0));
            flux.setValue(parseDoubleCorrectly(record.get(getValueHeaderByAtomIRS(a))));
            flux.setError(parseFloatCorrectly(record.get(getErrorHeaderByAtomIRS(a))));
            flux.setAperture(irsMode);

            FluxRepository.save(flux);
        }


    }

    private IRSHeader getErrorHeaderByAtomIRS(Atom a) {
        String s;
        for(IRSHeader h : IRSHeader.values()){
            s = h.toString();
            if(s.contains("ERR") && s.contains(a.toString())){
                return h;
            }
        }

        return null;
    }

    private IRSHeader getValueHeaderByAtomIRS(Atom a) {
        String s;
        for(IRSHeader h : IRSHeader.values()){
            s = h.toString();
            if(s.contains("FLUX") && s.contains(a.toString())){
                return h;
            }
        }

        return null;
    }

    private IRSHeader getLimitHeaderByAtomIRS(Atom a) {
        String s;
        for(IRSHeader h : IRSHeader.values()){
            s = h.toString();
            if(s.contains("LIM") && s.contains(a.toString())){
                return h;
            }
        }

        return null;
    }

    /**
     * gets IRS mode from String
     * @param s string to parse
     * @return aperture
     */
    private Aperture getIRSMode(String s) {
        if(s.contains(Aperture.IRS_HR_AND_LR.getAperture())){
            return Aperture.IRS_HR_AND_LR;
        } else if(s.contains(Aperture.IRS_HR.getAperture())){
            return Aperture.IRS_HR;
        } else if (s.contains(Aperture.IRS_LR.getAperture())){
            return Aperture.IRS_LR;
        } else {
            return Aperture.NOT_SPECIFIED;
        }
    }

    /**
     * first gets from DB galaxy related to flux (if present,else inserts it) and inserts flux into db
     * @param record csv file line
     */
    private void persistFlux(CSVRecord record) {


        Galaxy galaxy = returnGalaxyIfPresent(record.get(FluxHeader.GALAXY_NAME).trim());

        //same for every row
        Aperture aperture = getApertureByString(record.get(FluxHeader.APERTURE));

        List<Atom> atoms = new ArrayList<>();
        atoms.add(Atom.OIII52);
        atoms.add(Atom.NIII57);
        atoms.add(Atom.OI63);
        atoms.add(Atom.OIII88);
        atoms.add(Atom.NII122);
        atoms.add(Atom.OI145);
        atoms.add(Atom.CII158);


        /* for every atom persist relative flux */
        for(Atom a : atoms){
            Flux flux = new Flux();
            flux.setGalaxy(galaxy);
            flux.setFluxType(FluxType.LINE);
            flux.setAtom(a);
            flux.setUpperLimit(record.get(getLimitHeaderByAtomFlux(a)).charAt(0));
            flux.setValue(parseDoubleCorrectly(record.get(getValueHeaderByAtomFlux(a))));
            flux.setError(parseFloatCorrectly(record.get(getErrorHeaderByAtomFlux(a))));
            flux.setAperture(aperture);

            // persist
            FluxRepository.save(flux);
        }


    }

    private FluxHeader getErrorHeaderByAtomFlux(Atom a){

        for(FluxHeader head : FluxHeader.values()){
            if(head.toString().contains("ERR") && head.toString().contains(a.toString())){
                return head;
            }
        }
        return null;

    }

    private FluxHeader getValueHeaderByAtomFlux(Atom a) {
        for(FluxHeader head : FluxHeader.values()){
            if(head.toString().contains("FLUX") && head.toString().contains(a.toString())){
                return head;
            }
        }
        return null;

    }

    private FluxHeader getLimitHeaderByAtomFlux(Atom a) {


        for(FluxHeader head : FluxHeader.values()){
            if(head.toString().contains("LIM") && head.toString().contains(a.toString())){
                return head;
            }
        }
        return null;

    }


    /**
     * given a csv file with reads the header names (and writes the to the new file),removes legend and references; DELETES old file
     * @param file csv file with defined structure
     * @return path of new parsable file
     */
    private CSVDataFile createParsableCSVFile(File file){

        String columnNames = "";
        String newFileName = createNewFileName(file);
        FileWriter writer;
        BufferedReader bufferedReader;

        try {

            writer = new FileWriter(TMP_DIR + newFileName,true);
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int columnIndex = 0;
            boolean headerWritten = false;
            boolean fluxFile = file.getName().contains("4_flux");

            int index = 0;
            while ((line = bufferedReader.readLine()) != null){
                if(line.startsWith("-----")){
                    index++;
                    continue;
                } else if(index == 1){
                     columnIndex = line.indexOf("Label") - 2;
                } else if(index == 2){
                    int localColumnIndex;

                    if(line.charAt(columnIndex) == ' '){
                        localColumnIndex = columnIndex + 2;
                    } else {
                        localColumnIndex = columnIndex;
                    }
                    while (line.charAt(localColumnIndex) != ' '){
                        columnNames += line.charAt(localColumnIndex);
                        localColumnIndex++;
                    }

                    columnNames += ';';


                }else if(index == 3 && fluxFile){
                    writer.write(line + "\n");

                } else if(index == 4) {
                    if (!headerWritten) {
                        if(columnNames.startsWith("n;")){
                            columnNames = columnNames.substring(2,columnNames.length());
                        }

                        //writer.write(columnNames + "\n");
                        headerWritten = true;
                    }

                    writer.write(line + "\n");

                }
            }
            writer.close();
            bufferedReader.close();

            // TODO : put back
            //file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CSVDataFile(new File(TMP_DIR + newFileName));
    }

    /**
     * creates a new temporary file name of new parsable file to be written
     * @param file old file
     * @return new file name
     */
    private String createNewFileName(File file) {
        Random random = new Random();
        Integer r = random.nextInt(100000);
        String newFileName = file.getName().replace(".csv",r.toString()).concat(".csv");


        return newFileName;
    }


}
