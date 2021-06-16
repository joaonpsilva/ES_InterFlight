/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterFlight.Model;

import java.util.HashMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author Duarte Henriques
 */
@Component
public class AircraftList {
    private static final HashMap<String, Flight> mapper = new HashMap<String, Flight>();

    public static void add(String icao, Flight airplane) {
        mapper.put(icao, airplane);
    }

    static String getPlanesByIcao(String icao24) {
        if (mapper.containsKey(icao24)) {
            Flight temp = mapper.get(icao24);
            return temp.getModel();
        } else {
            return "icao24 not present in the DataBase";
        }
    }

    public static String toString(String icao24) {
        Flight temp = mapper.get(icao24);
        return temp.toString();
    }

    public static boolean checkExistence(String icao24) {
        return mapper.containsKey(icao24);
    }
    

    public static Flight mergeDetails(Flight plane){
        Flight temp = mapper.get(plane.getIcao24());
        plane.setRegistration(temp.getRegistration());
        plane.setManufacturername(temp.getManufacturername());
        plane.setModel(temp.getModel());
        plane.setOwner(temp.getOwner());
        plane.setOperator(temp.getOperator());
        return plane;
    }

    private AircraftList() {
    }

    public static int size() {
        return mapper.size();
    }
}