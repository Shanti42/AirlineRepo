package airtravel.tests;

import airtravel.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Flight interface, and the AbstractFlight and SimpleFlight classes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightTest {

    public static String flightCode;
    public static String originCode;
    public static String destinationCode;

    public static LocalTime depart;
    public static LocalTime arrival;

    public static Duration originDuration;
    public static Duration destDuration;

    public static Airport origin;
    public static Airport destination;

    public static Leg leg;
    public static FlightSchedule flightSchedule;
    public static Flight flight;

    @BeforeAll
    void initializeFlights() {
        flightCode = "A112";
        originCode = "CLE";
        destinationCode = "LGA";

        depart = LocalTime.MIN;
        arrival = LocalTime.NOON;

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
    void testSimpleFlightOf(){


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
    void testSimpleFlightIsShort() {
        Duration nullDuration = null;

        Assertions.assertThrows(NullPointerException.class, () -> {flight.isShort(nullDuration);});

        Duration duration = Duration.ofHours(10);
        Assertions.assertNotNull(flight.isShort(duration));
    }

    @Test
    void testFlightGroupOf(){

        FlightGroup flightGroup = FlightGroup.of(origin);
        Assertions.assertThrows(NullPointerException.class, () -> {FlightGroup.of(null);});
    }

    @Test
    void testFlightGroupAdd(){

        FlightGroup flightGroup = FlightGroup.of(origin);
        Assertions.assertTrue(flightGroup.add(flight));
        Assertions.assertFalse(flightGroup.add(flight));
    }

    @Test
    void testFlightGroupRemove() {

        FlightGroup flightGroup = FlightGroup.of(origin);
        Assertions.assertFalse(flightGroup.remove(flight));
        flightGroup.add(flight);
        Assertions.assertTrue(flightGroup.remove(flight));

    }

    @Test
    void testFlightGroupFlightsAtOrAfter() {

        FlightGroup flightGroup = FlightGroup.of(origin);

        Assertions.assertNotNull(flightGroup.flightsAtOrAfter(depart));
        Assertions.assertNotNull(flightGroup.flightsAtOrAfter(LocalTime.MIDNIGHT));
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
