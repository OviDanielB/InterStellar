package View;

/**
 * Bean containg galaxy query results
 */
public class GalaxyResultBean {
    private String name;

    //position
    private int ascH;
    private int ascM;
    private float ascS;
    private char decSign;
    private int decD;
    private int decM;
    private float decS;

    private float distance;
    private float redshift;

    private float lumAvg;

    private float metallicity;
    private float metallicityError;

    public GalaxyResultBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAscH() {
        return ascH;
    }

    public void setAscH(int ascH) {
        this.ascH = ascH;
    }

    public int getAscM() {
        return ascM;
    }

    public void setAscM(int ascM) {
        this.ascM = ascM;
    }

    public float getAscS() {
        return ascS;
    }

    public void setAscS(float ascS) {
        this.ascS = ascS;
    }

    public char getDecSign() {
        return decSign;
    }

    public void setDecSign(char decSign) {
        this.decSign = decSign;
    }

    public int getDecD() {
        return decD;
    }

    public void setDecD(int decD) {
        this.decD = decD;
    }

    public int getDecM() {
        return decM;
    }

    public void setDecM(int decM) {
        this.decM = decM;
    }

    public float getDecS() {
        return decS;
    }

    public void setDecS(float decS) {
        this.decS = decS;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getRedshift() {
        return redshift;
    }

    public void setRedshift(float redshift) {
        this.redshift = redshift;
    }

    public float getLumAvg() {
        return lumAvg;
    }

    public void setLumAvg(float lumAvg) {
        this.lumAvg = lumAvg;
    }


    public float getMetallicity() {
        return metallicity;
    }

    public void setMetallicity(float metallicity) {
        this.metallicity = metallicity;
    }

    public float getMetallicityError() {
        return metallicityError;
    }

    public void setMetallicityError(float metallicityError) {
        this.metallicityError = metallicityError;
    }

    @Override
    public String toString() {
        return "GalaxyResultBean{" +
                "name='" + name + '\'' +
                ", ascH=" + ascH +
                ", ascM=" + ascM +
                ", ascS=" + ascS +
                ", decSign=" + decSign +
                ", decD=" + decD +
                ", decM=" + decM +
                ", decS=" + decS +
                ", distance=" + distance +
                ", redshift=" + redshift +
                ", lumAvg=" + lumAvg +
                ", metallicity=" + metallicity +
                ", metallicityError=" + metallicityError +
                '}';
    }
}
