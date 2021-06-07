package InterFlight.Controllers;

import InterFlight.Model.Flight;
import InterFlight.Services.FlightHistoryService;
import InterFlight.Services.RealTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ControllerFlightHistory {

    @Autowired
    FlightHistoryService flightHistory;


    @GetMapping("/getNumPlanesByOriginCountry/{originCountry}")
    List<Flight> getNumPlanesByOriginCountry(@PathVariable String originCountry)
    {
        return flightHistory.getNumPlanesOrigin(originCountry);
    }

}
