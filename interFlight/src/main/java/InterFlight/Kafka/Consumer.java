package InterFlight.Kafka;

import org.springframework.stereotype.Service;

import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.kafka.annotation.KafkaListener;

<<<<<<< HEAD
=======


>>>>>>> 7bff883d97db0fdccb66fe405aefb279443573b5
@Service
public class Consumer {
    
    private static final String TOPIC = "interFlight";
    
    ObjectMapper objectMapper = new ObjectMapper();
    //ArrayList with all the Refresh data 
    //!!!!!!!!!!!!!!!!!!! Falta retirar os avioes que aterram no array. (Talvez criar um evento para ver quais sao os avioes que aterram para os retirar deste arrayList??)
    ArrayList<Flight> realTimeFlights = new ArrayList<Flight>(); 

    //@Autowired
    //private KafkaTemplate<String, Flight> kafkaTemplate;

    @KafkaListener(topics = TOPIC, groupId = "1")
    public void consume(String message) throws IOException {
        System.out.println("## -> Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);	
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
            }
        }
    }
    
    //All Flights in real time
    public ArrayList<Flight> getRealTimeFlights() {
        return realTimeFlights;
    }
}
