package InterFlight.Services;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FlightHistoryService {

    private static final String flightinit = "flightInitiated";

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    FlightRepository repo;

    @KafkaListener(topics = flightinit, groupId = "1")
    public void consumeInitiaded(String message) throws IOException {
        System.out.println("## -> HISTORY Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        System.out.println("-----------------------------+++++++++++++++++++++------------"+ flight.getDate());

        repo.save(flight);
    }

    public ArrayList<Flight> getByPlanesByOrigin(String originCountry){
        return  (ArrayList<Flight>) repo.findByOriginCountry(originCountry);
    }

    public ArrayList<Flight> getAllPLanes(){
        return (ArrayList<Flight>) repo.findAll();
    }

}
