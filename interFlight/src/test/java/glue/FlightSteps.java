package glue;

import InterFlight.Dao.FlightRepository;
import InterFlight.Kafka.KafkaConsumer;
import InterFlight.Kafka.KafkaProducer;
import InterFlight.Model.Flight;
import InterFlight.Services.RealTimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightSteps {

    @Autowired
    private InterFlight.Kafka.KafkaConsumer KafkaConsumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RealTimeService realTimeService;

    @Value("${test.topic}")
    private String topic;

    List<Flight> flights = new ArrayList<>();

    List<Flight> actualFlights = new ArrayList<>();

    Flight actualFlight;

    String countryName;



    @Before
    public void setup()
    {

    }

    @Given("^flights from the service$")
    public void givenTheFollowingFlights(final List<Flight> flights)
    {

       for(int i = 0 ; i<flights.size() ; i++)
       {
           kafkaProducer.sendMessage(topic, flights.get(i).toString());
       }

    }

    @When("^the users requests all flights$")
    public void whenTheUserRequestsAllTheFlights() throws JsonProcessingException, InterruptedException {
        KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        actualFlights = realTimeService.getAllPlanes();
        System.out.println("flights->"+actualFlights.toString());
    }

    @Then("^all requested flights are returned$")
    public void allRequestedFlightsAreReturned()
    {
        assertEquals(actualFlights.size(), flights.size());
    }

    @Given("^a country name$")
    public void countryName(final String countryName)
    {

        this.countryName = countryName;

        for(int i = 0 ; i<flights.size() ; i++)
        {
            kafkaProducer.sendMessage(topic, flights.get(i).toString());
        }
    }

    @When("^the users requests flights from a country$")
    public void requestsSpecificFlights() throws JsonProcessingException, InterruptedException {
        KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        actualFlights = realTimeService.getAllPlanes();
    }

    @Then("^return all flights from that country$")
    public void returnAllSpecificFlights()
    {
        for(int i = 0 ; i<flights.size() ; i++)
        {
            if(!flights.get(i).toString().contains(countryName))
            {
                assertEquals(actualFlights.size(), flights.size());
            }
        }
    }


    @Given("^some flights$")
    public void specificPlane(final List<Flight> flights)
    {
        this.flights = flights;
        for(int i = 0 ; i<flights.size() ; i++)
        {
            kafkaProducer.sendMessage(topic, flights.get(i).toString());
        }
    }

    @When("^the user requests one type of plane$")
    public void oneType(final String type) throws JsonProcessingException, InterruptedException {
        KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        Flight actualFlight = realTimeService.getSpecificPlane(type);
    }

    @Then("^return all flights from that type of plane$")
    public void specificFlightOne()
    {
        assertEquals(actualFlight, flights.get(0));
    }



}
