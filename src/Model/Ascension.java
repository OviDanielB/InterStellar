package Model;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.*;

/**
 * Indicates one of two angular coordinates to identify a galaxy in space
 */
@Embeddable
public class Ascension {

    /**
     * Default constructor
     */
    public Ascension() {
    }

    /**
     * ascension in hours
     */
    @Column(columnDefinition = "numeric(2,0) default -1" )
    private int ascensionHour;

    /**
     * ascension in minutes
     */
    @Column(columnDefinition = "numeric(2,0) default -1")
    private int ascensionMin;

    /**
     * ascension in seconds
     */
    @Column(columnDefinition = "numeric(7,4) default -1")
    private float ascensionSec;

    public int getAscensionHour() {
        return ascensionHour;
    }

    public void setAscensionHour(int ascensionHour) {
        this.ascensionHour = ascensionHour;
    }

    public int getAscensionMin() {
        return ascensionMin;
    }

    public void setAscensionMin(int ascensionMin) {
        this.ascensionMin = ascensionMin;
    }

    public float getAscensionSec() {
        return ascensionSec;
    }

    public void setAscensionSec(float ascensionSec) {
        this.ascensionSec = ascensionSec;
    }
}