import InterFlight.Controllers.ControllerStoragedData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ControllerStoragedDataTest {
    
    @Test
    void getPlane(){
        ControllerStoragedData controller = new ControllerStoragedData();
        String response = controller.getPlaneByIcao("ac130");
        assertEquals("ac130",response); 
    }

}

