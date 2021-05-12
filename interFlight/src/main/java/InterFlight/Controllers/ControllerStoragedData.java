/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterFlight.Controllers;

import InterFlight.Model.Flight;
import InterFlight.Services.RealTimeService;
import javax.lang.model.SourceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historic")
public class ControllerStoragedData {
    
    @Autowired
    RealTimeService realtimeService;

    @GetMapping("/getPlane/{icao24}")
    Flight getPlaneByIcao(@PathVariable String icao24)
    {
      return null;  
    }

}

