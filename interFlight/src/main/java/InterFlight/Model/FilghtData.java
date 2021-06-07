package InterFlight.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FilghtData {
    private List<Flight> flightdata;

    public FilghtData(List<Flight> data){
        this.flightdata = data;
    }

    public FilghtData(Flight data){
        this.flightdata = new ArrayList<>();
        this.flightdata.add(data);
    }


}
