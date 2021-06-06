import InterFlight.Controllers.ControllerStoragedData;
import InterFlight.interFlightApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {interFlightApplication.class,
                            CucumberIT.class},
                            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@CucumberOptions(plugin = {"pretty"}, tags = "",features="src/test/resources/features")
public class CucumberIT {
    
     @Test
    void getPlane(){
        ControllerStoragedData controller = new ControllerStoragedData();
        String response = controller.getPlaneByIcao("ac130");
        assertEquals("ac130",response); 
    }

}

