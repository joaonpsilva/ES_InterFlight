/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterFlight.Dao;

import InterFlight.Model.AircraftList;
import InterFlight.Model.Flight;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 *
 * @author Duarte Henriques
 */

@Component
public class GetFromCSV implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFromCSV.class);

    @Autowired
    private ResourceLoader resourceLoader;

    private CSVParser parser;


    public void run(String... args) throws Exception {
        LOGGER.info("#### -> ADDING PLANES TO AIRCRAFT LIST");
        Resource resource = resourceLoader.getResource("classpath:aircraftDatabase.csv");
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        parser = new CSVParser(br, CSVFormat.EXCEL.withHeader().withTrim());

        Iterable<CSVRecord> records = parser.getRecords();
        for (CSVRecord r : records) {
            if (!r.get("icao24").isEmpty()) {
                if (!r.get("registration").isEmpty()) {
                    AircraftList.add(r.get("icao24"),
                            new Flight(r.get("icao24"),
                                    r.get("registration"),
                                    r.get("manufacturername"),
                                    r.get("model"),
                                    r.get("owner"),
                                    r.get("operator")
                            ));
                }
            }
        };
        LOGGER.info("#### -> NUMBER OF PLANES ADDED: " + AircraftList.size());
    }
}