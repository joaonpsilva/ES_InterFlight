package InterFlight.Services;

import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RealTimeService {

    private static final String TOPIC = "interFlight";
    
    ObjectMapper objectMapper = new ObjectMapper();
    //ArrayList with all the Refresh data 
    //!!!!!!!!!!!!!!!!!!! Falta retirar os avioes que aterram no array. (Talvez criar um evento para ver quais sao os avioes que aterram para os retirar deste arrayList??)
    List<Flight> realTimeFlights = new ArrayList<>(); 

    //@Autowired
    //private KafkaTemplate<String, Flight> kafkaTemplate;

    @KafkaListener(topics = TOPIC, groupId = "1")
    public void consume(String message) throws IOException {
        System.out.println("## -> Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);
        
        if(realTimeFlights.isEmpty())
        {
            realTimeFlights.add(flight); 
        }
        
        for (int i = 0; i < realTimeFlights.size() ; i++)
        {
            //Refresh ArrayList with info for the flight
            if(realTimeFlights.get(i).getIcao24().equals(flight.getIcao24()))
            {
                realTimeFlights.get(i).setIcao24(flight.getIcao24());
                realTimeFlights.get(i).setId(flight.getId());
                realTimeFlights.get(i).setLast_contact(flight.getLast_contact());
                realTimeFlights.get(i).setLatitude(flight.getLatitude());
                realTimeFlights.get(i).setLongitude(flight.getLongitude());
                realTimeFlights.get(i).setOriginCountry(flight.getOriginCountry());
                realTimeFlights.get(i).setVelocity(flight.getVelocity()); 
            }
            else
            {
                realTimeFlights.add(flight); 
                break;
            }
        }
    }
    
    //Ir buscar todos os avioes ao Kafka
    public List<Flight> getAllPlanes()
    {
        //Example
        return realTimeFlights;
    }

    //Ir buscar so os avioes de um certo pais
    public ArrayList<Flight> getFlightsFiltered(String country){

        return null;
    }
    
    //Ir buscar so os avioes de um certo modelo
    public Flight getSpecificPlane(String planeIcao24)
    {
        return null;
    }
}
