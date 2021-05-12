package InterFlight.Services;

import InterFlight.Kafka.Consumer;
import InterFlight.Model.Flight;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RealTimeService {

    Consumer realTimeData;
    
    //Ir buscar todos os avioes ao Kafka
    public ArrayList<Flight> getSearchs()
    {
        //Example
        return realTimeData.getRealTimeFlights();
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
