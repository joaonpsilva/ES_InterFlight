package InterFlight.Services;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FlightHistoryService {

    private static final String flighInitiated = "flightInitiaded";

    ObjectMapper objectMapper = new ObjectMapper();
    FlightRepository repo;

    @KafkaListener(topics = flighInitiated, groupId = "1")
    public void consumeInitiaded(String message) throws IOException {
        System.out.println("## -> Flight Initiaded -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        repo.save(flight);
    }

    public ArrayList<Flight> getNumPlanesOrigin(String originCountry){
        return (ArrayList<Flight>) repo.findByOriginCountry(originCountry);
    }


}
