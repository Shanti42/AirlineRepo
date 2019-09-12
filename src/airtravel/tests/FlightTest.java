package airtravel.tests;

import airtravel.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

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


    /**
     * Test FlightSchedule class
     */
    @Test
    void testFlightScheduleOf() {
        assertThrows(NullPointerException.class, () -> {
            FlightSchedule.of(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            FlightSchedule.of(depart, null);
        });
        assertThrows(NullPointerException.class, () -> {
            FlightSchedule.of(null, arrival);
        });
    }

    @Test
    void testFlightScheduleIsShort() {
        Duration oneHour = Duration.ofHours(1);
        Duration twoHour = Duration.ofHours(2);
        Duration mins30 = Duration.ofMinutes(30);
        LocalTime oneHourLoc = LocalTime.of(1, 0);
        LocalTime min30Loc = LocalTime.of(0, 30);
        LocalTime twoHourLoc = LocalTime.of(2, 0);
        FlightSchedule hourFlight = FlightSchedule.of(oneHourLoc, twoHourLoc);
        assertThrows(NullPointerException.class, () -> {
            hourFlight.isShort(null);
        });
        assertTrue(hourFlight.isShort(oneHour), "Duration equal to flight");
        assertFalse(hourFlight.isShort(mins30), "Duration shorter than flight");
        assertTrue(hourFlight.isShort(twoHour), "Duration longer than flight");
    }


}
