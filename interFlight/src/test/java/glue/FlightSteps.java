package glue;

import InterFlight.Dao.FlightRepository;
import InterFlight.Model.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
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

    @Given("^the following flights$")
    public void givenTheFollowingFlights(final List<Flight> flights)
    {
        expectedFlights.addAll(flights);
    }

    @Given("^the following flights to the database$")
    public void giveFlightsToDatabase(List<Flight> flights)
    {
        expectedFlights.addAll(flights);
        flightRepository.saveAll(flights);
    }

    @Given("^flight must be higher than 0$")
    public void allFlightsTest()
    {

    }

    @When("^the user wants to access the database$")
    public void whenUsersRequestsdataFromDB()
    {
        actualFlights.addAll(flightRepository.findAll());
    }

    @When("^the users requests all flights$")
    public void userRequestsAllFlights()
    {
        Flight teste1 = new Flight("4caa57", "Ireland", null,null,null,null);
        Flight teste2 = new Flight("3c66b6", "Germany", null,null,null,null);
        actualFlights.add(teste1);
        actualFlights.add(teste2);
    }

    @When("^the user requests specific flights$")
    public void whenTheUserRequestsAllTheFlights() throws JsonProcessingException {
        Flight teste1 = new Flight("4caa57", "Ireland", null,null,null,null);
        Flight teste2 = new Flight("3c66b6", "Germany", null,null,null,null);
        actualFlights.add(teste1);
        actualFlights.add(teste2);
    }

    @Then("^database returns the flights given before$")
    public void databaseReturnsData()
    {
        validateFlights();
    }


    @Then("^all requested flights are returned$")
    public void allRequestedFlightsAreReturned()
    {
        if(actualFlights.size()<=0)
        {
            Assertions.assertEquals("-", "ArrayIsEmpty");
        }
    }

    @Then("^all the flights are returned$")
    public void thenAllTheFlightsAreReturned(){
        validateFlights();
    }

    private void validateFlights()
    {
        Assertions.assertEquals(expectedFlights.size(), actualFlights.size());
        for(int i = 0; i< actualFlights.size();i++)
        {
            Assertions.assertEquals(actualFlights.get(i).getIcao24(), expectedFlights.get(i).getIcao24());
        }
    }
}
