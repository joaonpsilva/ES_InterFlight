
package InterFlight.Services;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StoragedDataService {
    
    
    FlightRepository storagedData;
    
    //Ir buscar todos os avioes ao Kafka
    public List<Flight> getSearchs()
    {
        //Example
        return storagedData.findAll();
    }

    //Ir buscar so os avioes de um certo pais
    public List<Flight> getFlightsFiltered(String country){

        return null;
    }
    
    //Ir buscar so os avioes de um certo modelo
    public Flight getSpecificPlane(String planeIcao24)
    {
        return null;
    }
    
}
