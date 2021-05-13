package InterFlight.Controllers;

import InterFlight.Model.Flight;
import InterFlight.Services.RealTimeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/realtime")
public class ControllerRealTime {
    
    @Autowired
    RealTimeService realtimeService;
    

    @GetMapping("/getPlane/{icao24}")
    Flight getPlaneByIcao(@PathVariable String icao24)
    {
      return null;  
    }
    
    @GetMapping("/getAllPlanes")
    List<Flight> getAllPlanes ()
    {
        return realtimeService.getAllPlanes();
    }
    
    
}
