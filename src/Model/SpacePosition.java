package Model;
import javax.persistence.*;
import java.util.*;

/**
 * represents a position in space (ascension and declination) to identify the position of a galaxy;
 */
@Embeddable
public class SpacePosition {

    /**
     * Default constructor
     */
    public SpacePosition() {
    }

    /**
     * ascension
     */
    @Embedded
    private Ascension ascension;

    /**
     * declination
     */
    @Embedded
    private Declination declination;


    public Ascension getAscension() {
        return ascension;
    }

    public void setAscension(Ascension ascension) {
        this.ascension = ascension;
    }

    public Declination getDeclination() {
        return declination;
    }

    public void setDeclination(Declination declination) {
        this.declination = declination;
    }
}