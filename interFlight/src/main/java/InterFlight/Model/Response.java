package InterFlight.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private Integer time;
    private String[][] states;
    
    public Response(){}

    public String[][] getStates() {
        return this.states;
    }

    public void setStates(String[][] states) {
        this.states = states;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "{" +
            " time='" + getTime() + "'" +
            "}";
    }


    
    
}
