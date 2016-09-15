package Model;
import javax.persistence.*;
import java.util.*;

/**
 * Abstract measure with ID and value. It can be Luminosity,Metallicity or Flux
 * MappedSuperclass esignates a class whose mapping information is applied to the entities that inherit from it
 * and has no table for itself (useless)
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Measure {

    /**
     * Default constructor
     */
    public Measure() {
    }



    /**
     * Each entity that inherits has its own id for its table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long measureID;

    /**
     * Value of measured quantity
     * Value can be missing so default is -1
     */
    @Column(columnDefinition = "real default -1")
    private double value;

    /**
     * JoinColumn : this entity is the owner of the relationship
     * (the corresponding table has a column with a foreign key to the referenced table)
     */
    @ManyToOne
    @JoinColumn
    private Galaxy galaxy;

    public long getMeasureID() {
        return measureID;
    }

    public void setMeasureID(int measureID) {
        this.measureID = measureID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Galaxy getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(Galaxy galaxy) {
        this.galaxy = galaxy;
    }
}