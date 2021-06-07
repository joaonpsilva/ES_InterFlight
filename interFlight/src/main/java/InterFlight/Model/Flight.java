package InterFlight.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sun.istack.NotNull;

import org.apache.log4j.Logger;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Flight implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icao24;
    private String originCountry;
    private String last_contact;
    private Float longitude;
    private Float latitude;
    private Float velocity;
    private static Logger logger = Logger.getLogger(Flight.class);

    protected Flight()
    {
    }
    
    public Flight(String icao24,String originCountry, String last_contact, Float longitude, Float latitude, Float velocity) {
        this.icao24 = icao24;
        this.originCountry = originCountry;
        this.last_contact = last_contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "{" +
            " icao24='" + getIcao24() + "'" +
            ", last_contact='" + getLast_contact() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", velocity='" + getVelocity() + "'" +
            "}";
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public String getLast_contact() {
        return last_contact;
    }
    
        /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the icao24
     */
    public String getIcao24() {
        return icao24;
    }

    /**
     * @param icao24 the icao24 to set
     */
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    /**
     * @return the originCountry
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * @param originCountry the originCountry to set
     */
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    /**
     * @param last_contact the last_contact to set
     */
    public void setLast_contact(String last_contact) {
        this.last_contact = last_contact;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the velocity
     */
    public Float getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(Float velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

}
