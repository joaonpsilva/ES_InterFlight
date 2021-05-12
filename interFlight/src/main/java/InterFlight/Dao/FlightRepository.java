package InterFlight.Dao;

import InterFlight.Model.Flight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  Flight findById(long id);
  Flight findByIcao24(String icao);
  List<Flight> findByOriginCountry(String originCountry);
}