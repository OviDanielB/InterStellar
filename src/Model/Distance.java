package Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Distance {

    public Distance() {
    }

    /**
     *  distance in megaparsec
     */
    @Column(columnDefinition = "numeric(7,3) default -1")
    private float distanceValue;

    /**
     * redshift of a galaxy
     */
    @Column(columnDefinition = "numeric(10,7) default -1")
    private float redshift;

    public float getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(float distance) {
        this.distanceValue = distance;
    }

    public float getRedshift() {
        return redshift;
    }

    public void setRedshift(float redshift) {
        this.redshift = redshift;
    }
}
