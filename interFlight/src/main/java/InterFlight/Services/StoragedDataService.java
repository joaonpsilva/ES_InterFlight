
package InterFlight.Services;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoragedDataService {
   
    
    @Autowired
    FlightRepository storagedData;
    
    public StoragedDataService()
    {
    }
    
    //Ir buscar todos os avioes a base de dados
    public List<Flight> getAllPlanes()
    {
        return storagedData.findAll();
    }

    //Ir buscar so os avioes de um certo pais
    public List<Flight> getFlightsFiltered(String country){

        return null;
    }
    
    //Ir buscar so os avioes de um certo modelo
    public List<Flight> getSpecificPlane(String planeIcao24)
    {
        //Mockup Data
        Flight mockup1 = new Flight("ac130","Portugal","0:15",16.5321F,12.123F,430.4F);
        Flight mockup2 = new Flight("ac131","Portugal","0:22",16.2312F,12.231F,460.4F);
        Flight mockup3 = new Flight("ac132","Portugal","0:01",16.123F,12.521F,420.4F);
        Flight mockup4 = new Flight("ac133","Portugal","0:22",16.754F,12.12F,410.4F);
        storagedData.save(mockup1);
        storagedData.save(mockup2);
        storagedData.save(mockup3);
        storagedData.save(mockup4);        
        return storagedData.findByIcao24(planeIcao24);
    }
    
}
