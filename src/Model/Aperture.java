package Model;

/**
 * indicates aperture for measurement
 */
public enum Aperture {
    LOW("3x3"),
    HIGH("5x5"),
    CENTRAL("c"),
    NOT_SPECIFIED("NULL"),
    IRS_LR("LR"),
    IRS_HR("HR"),
    IRS_HR_AND_LR("HR+LR");

    private String aperture;

    Aperture(String aperture) {
        this.aperture = aperture;
    }

    public String getAperture() {
        return aperture;
    }
}