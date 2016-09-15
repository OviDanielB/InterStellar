package Model;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.*;

/**
 * Indicates one of two angular coordinates to identify a galaxy in space
 */
@Embeddable
public class Declination {

    /**
     * Default constructor
     */
    public Declination() {
    }

    /**
     * declinations sign ( + or - )
     */
    @Column(columnDefinition = "char(1) default '!'")
    private char declinationSign;

    /**
     * declination degrees
     */
    @Column(columnDefinition = "numeric(2,0) default -1")
    private int declinationDegrees;

    /**
     * declination in minutes
     */
    @Column(columnDefinition = "numeric(2,0) default -1")
    private int declinationMin;

    /**
     * declination in seconds
     */
    @Column(columnDefinition = "numeric(7,4) default -1")
    private float declinationSec;

    public char getDeclinationSign() {
        return declinationSign;
    }

    public void setDeclinationSign(char declinationSign) {
        this.declinationSign = declinationSign;
    }

    public int getDeclinationDegrees() {
        return declinationDegrees;
    }

    public void setDeclinationDegrees(int declinationDegrees) {
        this.declinationDegrees = declinationDegrees;
    }

    public int getDeclinationMin() {
        return declinationMin;
    }

    public void setDeclinationMin(int declinationMin) {
        this.declinationMin = declinationMin;
    }

    public float getDeclinationSec() {
        return declinationSec;
    }

    public void setDeclinationSec(float declinationSec) {
        this.declinationSec = declinationSec;
    }
}