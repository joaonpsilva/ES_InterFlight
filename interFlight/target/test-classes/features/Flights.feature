Feature: Flight feature

    Scenario: A user wants all flights
        Given flights from the service
        When the users requests all flights
        Then all requested flights are returned

    Scenario: A user wants flights from a specific country
        Given a country name
        When the users requests flights from a country
        Then return all flights from that country