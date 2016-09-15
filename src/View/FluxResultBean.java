package View;

import Model.Atom;

/**
 * Bean that manages results from flux queries
 */
public class FluxResultBean {
    private String galaxyName;
    private double fluxValue;
    private String statisticsOperation;
    private Atom atom;
    private Atom denAtom;
    private float error;
    private boolean upperLimit;
    private String aperture;
    private boolean numUpperLimit;
    private boolean denUpperLimit;

    public FluxResultBean() {
    }

    public String getStatisticsOperation() {
        return statisticsOperation;
    }

    public void setStatisticsOperation(String statisticsOperation) {
        this.statisticsOperation = statisticsOperation;
    }

    public String getGalaxyName() {
        return galaxyName;
    }

    public void setGalaxyName(String galaxyName) {
        this.galaxyName = galaxyName;
    }

    public double getFluxValue() {
        return fluxValue;
    }

    public void setFluxValue(double fluxValue) {
        this.fluxValue = fluxValue;
    }

    public Atom getAtom() {
        return atom;
    }

    public void setAtom(Atom atom) {
        this.atom = atom;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public boolean isUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(boolean upperLimit) {
        this.upperLimit = upperLimit;
    }

    public boolean isNumUpperLimit() {
        return numUpperLimit;
    }

    public void setNumUpperLimit(boolean numUpperLimit) {
        this.numUpperLimit = numUpperLimit;
    }

    public boolean isDenUpperLimit() {
        return denUpperLimit;
    }

    public void setDenUpperLimit(boolean denUpperLimit) {
        this.denUpperLimit = denUpperLimit;
    }

    public Atom getDenAtom() {
        return denAtom;
    }

    public void setDenAtom(Atom denAtom) {
        this.denAtom = denAtom;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    @Override
    public String toString() {
        return "FluxResultBean{" +
                "galaxyName='" + galaxyName + '\'' +
                ", fluxValue=" + fluxValue +
                ", statisticsOperation='" + statisticsOperation + '\'' +
                ", atom=" + atom +
                ", denAtom=" + denAtom +
                ", error=" + error +
                ", upperLimit=" + upperLimit +
                ", aperture='" + aperture + '\'' +
                ", numUpperLimit=" + numUpperLimit +
                ", denUpperLimit=" + denUpperLimit +
                '}';
    }
}
