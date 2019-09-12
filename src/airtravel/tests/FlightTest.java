package airtravel.tests;

import airtravel.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalTime;
import java.time.Duration;
/**
 * Tests the Flight interface, and the AbstractFlight and SimpleFlight classes
 */
public class FlightTest {

    public String flightCode;
    public String originCode;
    public String destinationCode;

    public LocalTime depart;
    public LocalTime arrival;

    public Duration originDuration;
    public Duration destDuration;

    public Airport origin;
    public Airport destination;

    public Leg leg;
    public FlightSchedule flightSchedule;
    public Flight flight;

    @BeforeAll
    void initalilizeFlights() {
        flightCode = "A112";
        originCode = "CLE";
        destinationCode = "LGA";

        depart = LocalTime.MIN;
        arrival = LocalTime.MAX;

        originDuration = Duration.ofHours(5);
        destDuration = Duration.ofHours(12);

        origin = Airport.of(originCode, originDuration);
        destination = Airport.of(destinationCode, originDuration);

        leg = Leg.of(origin, destination);

        flightSchedule = FlightSchedule.of(depart, arrival);

        flight = SimpleFlight.of(flightCode, leg, flightSchedule);
    }
    /**
    *   SimpleFlight build method
     */
    @Test
    void SimpleFlightOf(){

        Assertions.assertSame(SimpleFlight.of(originCode,leg,flightSchedule),flight);
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,null,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,flightSchedule);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,leg,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode, null,flightSchedule);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,flightSchedule);});

    }
    /**
    * SimpleFlight - Test if isShort() throws exception when expected and returns a boolean otherwise
     */
    @Test
    void SimpleFlightIsShort() {
        Duration nullDuration = null;

        Assertions.assertThrows(NullPointerException.class, () -> {flight.isShort(nullDuration);});

        Duration duration = Duration.ofHours(10);
        Assertions.assertNotNull(flight.isShort(duration));
    }

    @Test
    void FlightGroupOf(){

        FlightGroup flightGroup = FlightGroup.of(origin);
        Assertions.assertThrows(NullPointerException.class, () -> {FlightGroup.of(null);});
    }

    @Test
    void FlightGroupAdd(){

      //  Flight
    }
}
