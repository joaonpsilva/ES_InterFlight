package InterFlight.Services;

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
    //ArrayList with all the Refresh data 
    //!!!!!!!!!!!!!!!!!!! Falta retirar os avioes que aterram no array. (Talvez criar um evento para ver quais sao os avioes que aterram para os retirar deste arrayList??)
    Map<String, Flight> realTimeFlights = new HashMap<>(); 

    @KafkaListener(topics = flightInfo, groupId = "1")
    public void consumeUpdate(String message) throws IOException {
        System.out.println("## -> REALTIME Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        
        realTimeFlights.put(flight.getIcao24(), flight);
    }

    @KafkaListener(topics = flightterminated, groupId = "1")    //remove flights from the list
    public void consumeTerminated(String message) throws IOException {
        System.out.println("## -> REALTIME Flight Over -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        
        realTimeFlights.remove(flight.getIcao24());
    }
    
    //Ir buscar todos os avioes ao Kafka
    public List<Flight> getAllPlanes()
    {
        //Example
        return new ArrayList<Flight>(realTimeFlights.values());
    }

    //Ir buscar so os avioes de um certo pais
    public ArrayList<Flight> getFlightsFiltered(String country){

        return null;
    }
    
    //Ir buscar so os avioes de um certo modelo
    public Flight getSpecificPlane(String planeIcao24)
    {
        if (realTimeFlights.containsKey(planeIcao24))
            return realTimeFlights.get(planeIcao24);
        return null;
    }
}
