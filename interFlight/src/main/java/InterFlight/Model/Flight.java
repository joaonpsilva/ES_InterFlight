package InterFlight.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sun.istack.NotNull;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "flight")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Flight implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icao24, originCountry, last_contact;
    private Float longitude, latitude, velocity;
    
    //novos atrb com os detalhes de cada aviao
    private String registration, manufacturername, model, owner, operator, callSign;
    
    
    
    
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
    
    // construtor a ser usado na classe AircraftList
    public Flight(String icao24, String registration, String manufacturername, String model, String owner, String operator) {
        this.icao24 = icao24;
        this.registration = registration;
        this.manufacturername = manufacturername;
        this.model = model;
        this.owner = owner;
        this.operator = operator;
    }

    
    /*
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
    */
    
    @Override
    public String toString() {
        return "{" +
            " icao24='" + getIcao24() + "'" +
            ", last_contact='" + getLast_contact() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", velocity='" + getVelocity() + "'" +
            ", registration='" + getRegistration() + "'" +
            ", manufacturername='" + getManufacturername() + "'" +
            ", model='" + getModel() + "'" +
            ", owner='" + getOwner() + "'" +
            ", operator='" + getOperator() + "'" +
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

}
