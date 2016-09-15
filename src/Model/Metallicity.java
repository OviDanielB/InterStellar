package Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.*;

/**
 * Metallicity measure of a particular galaxy
 */
@Entity
public class Metallicity extends Measure {

    /**
     * Default constructor
     */
    public Metallicity() {
    }

    /**
     * metallicity measurement error
     */
    @Column(columnDefinition = "numeric(4,2) default -1")
    private float error;

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }
}