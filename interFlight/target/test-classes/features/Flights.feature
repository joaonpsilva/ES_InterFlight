Feature: Flight feature

    Scenario: A user wants specific flights
        Given the following flights
        | icao24 | originCountry |
        | 4caa57 | Ireland |
        | 3c66b6 | Germany |
        When the user requests specific flights
        Then all the flights are returned

    Scenario: A user wants all flights
        Given flight must be higher than 0
        When the users requests all flights
        Then all requested flights are returned

    Scenario: Test if database is working properly
        Given the following flights to the database
        | icao24 | originCountry |
        | 4caa57 | Ireland |
        | 3c66b6 | Germany |
        When the user wants to access the database
        Then database returns the flights given before