package InterFlight.Controllers;

import InterFlight.Model.Flight;
import InterFlight.Services.RealTimeService;
import InterFlight.Model.FilghtData;

import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import java.time.Duration;

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
    
    /*@GetMapping("/getAllPlanes")
    List<Flight> getAllPlanes ()
    {
        return realtimeService.getAllPlanes();
    }*/

    @GetMapping(value = "/getAllPlanes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FilghtData> streamAll()
    {   

        return Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(12))
            .map(interval -> Collections.singletonList(new FilghtData(realtimeService.getAllPlanes())))
            .flatMapIterable(flights -> flights);
    }
    
    
}
