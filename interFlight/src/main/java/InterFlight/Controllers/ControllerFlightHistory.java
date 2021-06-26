package InterFlight.Controllers;

import InterFlight.Model.Flight;
import InterFlight.Services.FlightHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class ControllerFlightHistory {

    @Autowired
    FlightHistoryService flightHistory = new FlightHistoryService();


    @GetMapping("/getPlanesByOriginCountry/{originCountry}")
    List<Flight> getPlanesByOriginCountry(@PathVariable String originCountry)
    {
        return flightHistory.getByPlanesByOrigin(originCountry);
    }

    @GetMapping("/getAllPlanes")
    public List<Flight> getAllPlanes(){
        return flightHistory.getAllPLanes();
    }

}
