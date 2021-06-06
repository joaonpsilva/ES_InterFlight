Feature: Flight feature

    Scenario: A user wants all the current flights
        Given the the following flights
        | icao24 | originCountry |
        | 4caa57 | Ireland |
        | 3c66b6 | Germany |
        When the user requests all the flights
        Then all the flights are returned