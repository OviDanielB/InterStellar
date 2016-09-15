package View;

import Controller.GalaxyQueryController;
import utils.GalaxyQueryOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean that manages galaxy queries specified by operation
 */
public class GalaxyQueryBean {

    private String galaxyName;
    private String redshift;

    private String ascH;
    private String ascM;
    private String ascS;

    private String declSign;
    private String declM;
    private String declD;
    private String declS;

    private String many;

    private GalaxyQueryOperation operation;
    private String redshiftOpSign;

    public GalaxyQueryBean() {
    }

    /**
     * calls controller and executes query based on operation
     * @return
     */
    public List<GalaxyResultBean> performQuery(){
        List<GalaxyResultBean> result = new ArrayList<>();

        GalaxyQueryController controller = GalaxyQueryController.getInstance();

        switch (operation){
            case NAME:
                result = controller.getGalaxiesByName(this.galaxyName);
                break;
            case POSITION:
                result = controller.getGalaxiesByPosition(this);
                break;
            case REDSHIFT:
                result = controller.getGalaxiesByRedshift(this.redshift,this.redshiftOpSign,this.many);
                break;
            case NONE:
                break;
        }


        return result;
    }

    public String getRedshiftOpSign() {
        return redshiftOpSign;
    }

    public void setRedshiftOpSign(String redshiftOpSign) {
        this.redshiftOpSign = redshiftOpSign;
    }

    public String getMany() {
        return many;
    }

    public void setMany(String many) {
        this.many = many;
    }

    public String getGalaxyName() {
        return galaxyName;
    }

    public void setGalaxyName(String galaxyName) {
        this.galaxyName = galaxyName;
    }

    public String getRedshift() {
        return redshift;
    }

    public void setRedshift(String redshift) {
        this.redshift = redshift;
    }

    public String getAscH() {
        return ascH;
    }

    public void setAscH(String ascH) {
        this.ascH = ascH;
    }

    public String getAscM() {
        return ascM;
    }

    public void setAscM(String ascM) {
        this.ascM = ascM;
    }

    public String getAscS() {
        return ascS;
    }

    public void setAscS(String ascS) {
        this.ascS = ascS;
    }

    public String getDeclSign() {
        return declSign;
    }

    public void setDeclSign(String declSign) {
        this.declSign = declSign;
    }

    public String getDeclM() {
        return declM;
    }

    public void setDeclM(String declM) {
        this.declM = declM;
    }

    public String getDeclD() {
        return declD;
    }

    public void setDeclD(String declD) {
        this.declD = declD;
    }

    public String getDeclS() {
        return declS;
    }

    public void setDeclS(String declS) {
        this.declS = declS;
    }

    public GalaxyQueryOperation getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = getQueryOperation(operation);
    }

    /**
     * return operation type by string
     * @param operation string containing operation name
     * @return operation type
     */
    private GalaxyQueryOperation getQueryOperation(String operation) {
        for(GalaxyQueryOperation op : GalaxyQueryOperation.values()){
            if(op.toString().contains(operation)){
                return op;
            }
        }

        return GalaxyQueryOperation.NONE;
    }

    @Override
    public String toString() {
        return "GalaxyQueryBean{" +
                "galaxyName='" + galaxyName + '\'' +
                ", redshift='" + redshift + '\'' +
                ", ascH='" + ascH + '\'' +
                ", ascM='" + ascM + '\'' +
                ", ascS='" + ascS + '\'' +
                ", declSign='" + declSign + '\'' +
                ", declM='" + declM + '\'' +
                ", declD='" + declD + '\'' +
                ", declS='" + declS + '\'' +
                ", many='" + many + '\'' +
                ", operation=" + operation +
                '}';
    }
}
