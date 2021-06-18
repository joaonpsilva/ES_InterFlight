Feature: Flight feature

    Scenario: A user wants all flights
        Given flights from the service
            | icao24 | originCountry |
            | 4caa57 | Ireland |
            | 3c66b6 | Germany |
        When the users requests all flights
        Then all requested flights are returned

    Scenario: A user wants flights from a specific country
        Given a country name
        | Portugal |
        When the users requests flights from a country
        Then return all flights from that country

    Scenario: Get a type of a plane
        Given some flights
            | icao24 | originCountry |
            | 4caa57 | Ireland |
            | 3c66b6 | Germany |
        When the user requests one type of plane
            | 4caa57 |
        Then return all flights from that type of plane