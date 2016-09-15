package Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

/**
 * Flux measurement for a certain galaxy. Can be associated to a line or simply continuous (defined by FluxType)
 */
@Entity
public class Flux extends Measure {

    /**
     * Default constructor
     */
    public Flux() {
    }

    /**
     * flux measurement error; if not defined, -1 as default
     */
    @Column(columnDefinition = "numeric(6,2) default -1")
    private float error;

    /**
     * indicates if the measure is an upperlimit
     */
    @Column(columnDefinition = "char(1) default '!'")
    private char upperLimit;

    /**
     * flux type: continuous or line
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 15)
    private FluxType fluxType;

    /**
     * atom referred to the measure
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Atom atom;

    /**
     * aperture for the measurement (3x3,5x5 or c)
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Aperture aperture;

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public char getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(char upperLimit) {
        this.upperLimit = upperLimit;
    }

    public FluxType getFluxType() {
        return fluxType;
    }

    public void setFluxType(FluxType fluxType) {
        this.fluxType = fluxType;
    }

    public Atom getAtom() {
        return atom;
    }

    public void setAtom(Atom atom) {
        this.atom = atom;
    }

    public Aperture getAperture() {
        return aperture;
    }

    public void setAperture(Aperture aperture) {
        this.aperture = aperture;
    }

    @Override
    public String toString() {
        return "Flux{" +
                "error=" + error +
                ", upperLimit=" + upperLimit +
                ", fluxType=" + fluxType +
                ", atom=" + atom +
                ", aperture=" + aperture +
                '}';
    }
}