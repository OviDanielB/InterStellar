package Model;

import utils.CSVFileType;

import java.io.File;

/**
 * Represents a CSV file containg data
 */
public class CSVDataFile {
    private File file;
    private CSVFileType type;
    private static char delimiter = ';';


    public CSVDataFile(String path) {
        this.file = new File(path);
        this.type = CSVFileType.findTypeByFileName(path);
        System.out.println(type);
        System.out.println(path);

    }

    public CSVDataFile(File file){
        this.file = file;
        this.type = CSVFileType.findTypeByFileName(file.getName());
        System.out.println(type);
        System.out.println(file.getAbsolutePath());

    }

    public String getFileName(){
        return file.getName();
    }

    public boolean deleteFile(){

        return file.delete();
    }

    public static char getDelimiter() {
        return delimiter;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public CSVFileType getType() {
        return type;
    }

    public void setType(CSVFileType type) {
        this.type = type;
    }
}
