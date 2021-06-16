package InterFlight.Services;

import InterFlight.Model.AircraftList;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RealTimeService {

    private static final String flightInfo = "flightInfo";
    private static final String flightterminated = "flightTerminated";
    
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Flight> realTimeFlights = new HashMap<>(); 

    @KafkaListener(topics = flightInfo, groupId = "1")
    public void consumeUpdate(String message) throws IOException {
        //System.out.println("## -> REALTIME Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        
        Flight flightWithDetails;
        if(AircraftList.checkExistence(flight.getIcao24()))
        {
            flightWithDetails = AircraftList.mergeDetails(flight);
            System.out.println("## -> REALTIME Consumed message -> " +flightWithDetails.toString());
            realTimeFlights.put(flight.getIcao24(), flightWithDetails);
        }
        
        
        //realTimeFlights.put(flight.getIcao24(), flight);
    }

    @KafkaListener(topics = flightterminated, groupId = "1")    //remove flights from the list
    public void consumeTerminated(String message) throws IOException {
        //System.out.println("## -> REALTIME Flight Over -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        System.out.println("## -> REALTIME Flight Over -> " + flight.toString());
        realTimeFlights.remove(flight.getIcao24());
    }
    
    //Ir buscar todos os avioes ao Kafka
    public List<Flight> getAllPlanes()
    {
        //Example
        return new ArrayList<Flight>(realTimeFlights.values());
    }

    //Ir buscar so os avioes de um certo pais
    public List<Flight> getFlightsFiltered(String country){
        List<Flight> toReturn = new ArrayList<>();
        for (Flight f : realTimeFlights.values()){
            if (f.getOriginCountry().equals(country)){
                toReturn.add(f);
            }

        }
        return toReturn;
    }
    
    //Ir buscar so os avioes de um certo modelo
    public Flight getSpecificPlane(String planeIcao24)
    {
        if (realTimeFlights.containsKey(planeIcao24))
            return realTimeFlights.get(planeIcao24);
        return null;
    }
}
