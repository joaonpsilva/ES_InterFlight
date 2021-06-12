package InterFlight.Sensors;

import org.springframework.web.client.RestTemplate;

import InterFlight.Sensors.Kafka.Producer;
import InterFlight.Sensors.Model.Flight;
import InterFlight.Sensors.Model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import org.apache.log4j.Logger;

@Service
public class ApiCaller {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Producer kafkaProducer;

    final static Logger logger = Logger.getLogger(ApiCaller.class);
    
    private Set<Flight> flights = new HashSet<>();

    @Scheduled(fixedDelay = 10000L) //10 segs
    void callApi() throws InterruptedException {
        String url = "https://opensky-network.org/api/states/all?lamin=35.975495&lomin=-10.363099&lamax=71.145871&lomax=41.960568";
        Response response = this.restTemplate.getForObject(url, Response.class);
        logger.debug("RECEIVED-----------------------------");

        Set<Flight> newflights = new HashSet<>();   //new flight information

        for (String[] state : response.getStates()){
            try{
                Flight flight = new Flight(state[0], 
                                            state[2], 
                                            Long.parseLong(state[4]), 
                                            Float.parseFloat(state[5]), 
                                            Float.parseFloat(state[6]), 
                                            Float.parseFloat(state[9]));
                            
            
                logger.debug("New Info " + flight);
                kafkaProducer.sendFlightUpdate(flight);
                newflights.add(flight);
            }catch(Exception e){}

        }

        for (Flight f : this.flights){      //iterate over old flight data and check if any plane is missing
            if (!newflights.contains(f)){
                logger.debug("Flight OVER " + f);
                kafkaProducer.sendFlightTerminated(f);
            }
        }

        for (Flight f : newflights){         //iterate over new flight data and check if any plane is new
            if (!this.flights.contains(f)){
                logger.debug("Flight Initiated " + f);
                kafkaProducer.sendFlightInitiated(f);
            }
        }

        this.flights = newflights;
    }
}