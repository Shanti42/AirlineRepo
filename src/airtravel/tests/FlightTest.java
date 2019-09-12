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

    @Test
    void TestOf(){

        Assertions.assertThrows(NullPointerException.class, SimpleFlight::of(null,null,null));

    }

    @Test
    void testIsShort() {
        Duration durationMax = null;
    }
}
