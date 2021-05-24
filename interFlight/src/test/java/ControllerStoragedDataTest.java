import InterFlight.Controllers.ControllerStoragedData;
import java.util.List;
import javax.lang.model.SourceVersion;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historic")
public class ControllerStoragedDataTest {
    
    @Test
    void getPlane(){
        ControllerStoragedData controller = new ControllerStoragedData();
        String response = controller.getPlaneByIcao("ac130");
        assertEquals("ac130",response); 
    }

}

