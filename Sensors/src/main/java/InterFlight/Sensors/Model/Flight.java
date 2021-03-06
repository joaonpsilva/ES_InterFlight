package InterFlight.Sensors.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import org.apache.log4j.Logger;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Flight implements Serializable{

    private Long id;
    private String icao24;
    private String originCountry;
    private Long last_contact;
    private Float longitude;
    private Float latitude;
    private Float velocity;
    private static Logger logger = Logger.getLogger(Flight.class);

    protected Flight()
    {
    }
    
    public Flight(String icao24,String originCountry, Long last_contact, Float longitude, Float latitude, Float velocity) {
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

    @Override
    public boolean equals(Object other){
        // if both the object references are 
        // referring to the same object.
        if(this == other)
            return true;
        
        //check class
        if(other == null || other.getClass()!= this.getClass())
            return false;
        
        // type casting of the argument. 
        Flight compFlight = (Flight) other;
        
        // comparing the state of argument with 
        // the state of 'this' Object.
        return compFlight.getIcao24().equals(this.getIcao24());
    }

    @Override
    public int hashCode()
    {
        return this.icao24.hashCode();  //icao is unique
    }


    public String getLast_contact() {

        int seconds = (int) (System.currentTimeMillis() / 1000L - (last_contact - 120));
        int minutes = seconds / 60; 
        seconds %= 60;
        String s = minutes + ":" + String.format("%02d", seconds);
        return s;
    }

    public Float getLongitude() {
        return this.longitude;
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
    public void setLast_contact(Long last_contact) {
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
