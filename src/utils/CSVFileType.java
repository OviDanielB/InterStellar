package utils;

import java.util.ArrayList;

/**
 * specifies csv data file type
 */
public enum  CSVFileType {

    SAMPLE(3,"sample"),
    FLUX(4,"4_flux"),
    CONT(6,"cont"),
    IRS(8,"irs"),
    APER_FLUX(11,"11_C_3x3_5x5_flux");

    private int type;
    private String fileName;

    CSVFileType(int type,String fileName) {
        this.type = type;
        this.fileName = fileName;
    }

    public int getType() {
        return type;
    }

    public static ArrayList<String> getFileNameValues(){
        ArrayList<String> names= new ArrayList<>();
        for(CSVFileType t : values()){
            names.add(t.fileName);
        }

        return names;
    }

    public static CSVFileType findTypeByFileName(String fileName) {
        for(CSVFileType t : values()){
            if(fileName.contains(t.fileName)){
                return t;
            }
        }

        return null;
    }
}
