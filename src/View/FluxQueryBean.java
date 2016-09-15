package View;

import Controller.FluxQueryController;
import Model.Atom;
import utils.FluxQueryOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean used to specify flux query specified by 'operation'
 */
public class FluxQueryBean {

    private String galaxyName;
    private FluxQueryOperation operation;
    private String aperture;
    private String category;

    private Atom fluxNum;
    private Atom fluxDen;
    private List<Atom> atoms;


    /**
     * calls controller and executes query depending on operation
     * @return
     */
    public List<FluxResultBean> performQuery() {
        List<FluxResultBean> result;

        FluxQueryController controller = FluxQueryController.getInstance();

        switch (this.operation){
            case NAME:
                result = controller.getFluxByGalaxyName(this.galaxyName, this.atoms);
                break;
            case RATIO:
                result = controller.getFluxRatio(this.galaxyName,this.fluxNum,this.fluxDen);
                break;
            case RATIO_CONT:
                result = controller.getFluxRatioCont(this.galaxyName,this.fluxNum);
                break;
            default:
                result = controller.getFluxRatioStatistics(this.category,this.fluxNum,this.fluxDen,this.aperture,this.operation);
                break;
        }

        return result;
    }

    public FluxQueryBean() {
        atoms = new ArrayList<>();
    }

    public void addAtom(Atom a){
        atoms.add(a);
    }

    public String getGalaxyName() {
        return galaxyName;
    }

    public void setGalaxyName(String galaxyName) {
        this.galaxyName = galaxyName;
    }

    public FluxQueryOperation getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = getFluxTypeOperation(operation);
    }

    private FluxQueryOperation getFluxTypeOperation(String operation) {
        for(FluxQueryOperation op : FluxQueryOperation.values()){
            if(op.toString().contains(operation)){
                return op;
            }
        }
        return FluxQueryOperation.NONE;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Atom getFluxNum() {
        return fluxNum;
    }

    public void setFluxNum(Atom fluxNum) {
        this.fluxNum = fluxNum;
    }

    public Atom getFluxDen() {
        return fluxDen;
    }

    public void setFluxDen(Atom fluxDen) {
        this.fluxDen = fluxDen;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    public void setAtoms(List<Atom> atoms) {
        this.atoms = atoms;
    }

    @Override
    public String toString() {
        return "FluxQueryBean{" +
                "galaxyName='" + galaxyName + '\'' +
                ", operation=" + operation +
                ", aperture='" + aperture + '\'' +
                ", category='" + category + '\'' +
                ", fluxNum=" + fluxNum +
                ", fluxDen=" + fluxDen +
                ", atoms=" + atoms +
                '}';
    }
}
