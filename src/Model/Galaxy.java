package Model;

import javax.persistence.*;

/**
 * Represents a galaxy with a name, a position in space,the spectrum category to which it belongs and alternative names
 */
@Entity
public class Galaxy {

    /**
     * Default constructor
     */
    public Galaxy() {
    }

    /**
     * unique galaxy name
     */
    @Id
    private String name;

    /**
     * other names for galaxy
     */
    private String alternativeName;

    /**
     * category of galaxy based on spectrum
     */
    private String spectralClassification;


    /**
     * position in space
     */
    @Embedded
    private SpacePosition spacePosition;

    /**
     * distance with relative redshift
     */
    @Embedded
    private Distance distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getSpectralClassification() {
        return spectralClassification;
    }

    public void setSpectralClassification(String spectralClassification) {
        this.spectralClassification = spectralClassification;
    }

    public SpacePosition getSpacePosition() {
        return spacePosition;
    }

    public void setSpacePosition(SpacePosition spacePosition) {
        this.spacePosition = spacePosition;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "name='" + name + '\'' +
                ", alternativeName='" + alternativeName + '\'' +
                ", spectralClassification='" + spectralClassification + '\'' +
                ", spacePosition=" + spacePosition +
                ", distance=" + distance +
                '}';
    }
}