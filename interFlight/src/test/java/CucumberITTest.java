import InterFlight.Controllers.ControllerStoragedData;
import InterFlight.interFlightApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {interFlightApplication.class,
                            CucumberITTest.class},
                            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@CucumberOptions(plugin = {"pretty"}, tags = "",features="src/test/resources/features")
@ContextConfiguration
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class CucumberITTest {
  
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void getPlane(){
        ControllerStoragedData controller = new ControllerStoragedData();
        String response = controller.getPlaneByIcao("ac130");
        assertEquals("ac130",response); 
    }

}

