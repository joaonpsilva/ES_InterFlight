package InterFlight.Services;

import InterFlight.Model.AircraftList;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SpotterService {

    private static final String flightInfo = "flightInfo";
    private static final String flightterminated = "flightTerminated";

    ObjectMapper objectMapper = new ObjectMapper();
    //ArrayList with all the Refresh data 
    //!!!!!!!!!!!!!!!!!!! Falta retirar os avioes que aterram no array. (Talvez criar um evento para ver quais sao os avioes que aterram para os retirar deste arrayList??)
    Map<String, Set<Flight>> knownmodels = new HashMap<>();

    @KafkaListener(topics = flightInfo, groupId = "2")
    public void consumeUpdate(String message) throws IOException {
        //System.out.println("## -> SPOTTER Consumed message -> " +message);
        Flight flight = objectMapper.readValue(message, Flight.class);

        Flight flightWithDetails;
        if (AircraftList.checkExistence(flight.getIcao24())) {
            flightWithDetails = AircraftList.mergeDetails(flight);
            System.out.println("## -> SPOTTER Consumed message -> " + flightWithDetails.toString());
            String model = InferModel(flightWithDetails.getIcao24());
            if (!knownmodels.containsKey(model)) {
                knownmodels.put(model, new HashSet<Flight>());
            }

            Set<Flight> modelFlights = knownmodels.get(model);
            //update
            modelFlights.remove(flight); //if exists remove it
            modelFlights.add(flight);
        }
    }

    @KafkaListener(topics = flightterminated, groupId = "2")    //remove flights from the list
    public void consumeTerminated(String message) throws IOException {
        //System.out.println("## -> SPOTTER Flight Over -> " +message);

        Flight flight = objectMapper.readValue(message, Flight.class);
        String model = InferModel(flight.getIcao24());

        Flight flightWithDetails;
        if (AircraftList.checkExistence(flight.getIcao24())) {
            flightWithDetails = AircraftList.mergeDetails(flight);
            System.out.println("## -> SPOTTER Flight Over -> " + flightWithDetails.toString());

            //Maybe persist this?
            knownmodels.get(model).remove(flightWithDetails);
        }
    }

    /*Dummy way of getting the plane model*/
    private String InferModel(String icao24) {
        String[] models = {"ModelA", "ModelB", "ModelC", "ModelD"};
        return models[Math.abs(icao24.hashCode()) % 4];
    }

}
