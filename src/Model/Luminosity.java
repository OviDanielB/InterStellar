package Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

/**
 * Measure of luminosity for a galaxy
 */
@Entity
public class Luminosity extends Measure {

    /**
     * Default constructor
     */
    public Luminosity() {
    }

    /**
     * Indicates whether is an upper limit; when not present, default char '!' inserted
     */
    @Column(columnDefinition = "char(1) default '!'")
    private char upperLimit;

    /**
     * Atom to which the luminosity is measured by
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10,nullable = false)
    private Atom atom;

    public char getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(char upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Atom getAtom() {
        return atom;
    }

    public void setAtom(Atom atom) {
        this.atom = atom;
    }
}