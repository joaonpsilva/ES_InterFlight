package glue;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Flight> expectedFlights;

    private List<Flight> actualFlights;

    @Before
    public void setup()
    {
        expectedFlights = new ArrayList<>();
        actualFlights = new ArrayList<>();
        flightRepository.deleteAll();
    }

    @Given("^the the following flights$")
    public void givenTheFollowingFlights(final List<Flight> flights)
    {
        expectedFlights.addAll(flights);
        flightRepository.saveAll(flights);
    }

    @When("^the user requests all the flights$")
    public void whenTheUserRequestsAllTheFlights() throws JsonProcessingException {

    }

    @Then("^all the flights are returned$")
    public void thenAllTheFlightsAreReturned(){
        validateFlights();
    }

    private void validateFlights()
    {

    }
}
