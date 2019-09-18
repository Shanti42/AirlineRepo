package airtravel.tests;

import airtravel.*;
import airtravel.tests.AirportCodes.*;
import org.junit.jupiter.api.Test;


import java.util.EnumMap;

import static airtravel.SeatClass.*;
import static airtravel.tests.AirportCodes.CLE;
import static org.junit.jupiter.api.Assertions.*;


public class SeatTest extends FlightTest{


    /**
     * Test SeatConfiguration
     */
    @Test
    void testSeatConfigurationOf() {

        assertThrows(NullPointerException.class, () -> {
            SeatConfiguration.of(null);
        }, "SeatConfigOf -> Check catches null value");
        assertEquals(config1.seats(BUSINESS), 15, "Test Seats initialized and value correct");
    }

    @Test
    void testSeats() {
        assertEquals(config1.seats(ECONOMY), 10, "Test value assigned and retrieved from seats");
        assertEquals(config1.seats(PREMIUM_ECONONMY), 0, "Test null values for seats return 0");
        assertEquals(config2.seats(ECONOMY), 0, "Test seat value 0 on negative");
        assertEquals(config2.seats(BUSINESS), 0, "Tests seat value 0 on 0");
    }

    @Test
    void testSetSeats() {
        EnumMap<SeatClass, Integer> seats = new EnumMap<>(SeatClass.class);
        SeatConfiguration config = SeatConfiguration.of(seats);
        assertEquals(config.setSeats(ECONOMY, 10), 0, "Test setting seats from null Economy");
        assertEquals(config.setSeats(PREMIUM_ECONONMY, 15), 0, "Test setting seats from null Premium Economy");
        assertEquals(config.setSeats(BUSINESS, 20), 0, "Test setting seats from null Business");
        assertEquals(config.setSeats(BUSINESS, 15), 20, "Test setting value over existing");
        assertEquals(config.setSeats(BUSINESS, 1), 15, "Test setting value over existing saved");
    }

    @Test
    void hasSeats() {
        EnumMap<SeatClass, Integer> seats = new EnumMap<>(SeatClass.class);
        SeatConfiguration config = SeatConfiguration.of(seats);
        assertFalse(config.hasSeats(), "Test false when seats empty");
        assertFalse(config2.hasSeats(), "Test false when lots of zeros and nulls");
        assertTrue(config1.hasSeats(), "Test true when there are seats");
    }

    /**
     * Test seats available
     */

    @Test
    void testSimpleFlightSeatsAvailable() {

        assertThrows(NullPointerException.class, () -> {
            flight.seatsAvailable(null);
        }, "check catches null fare class");
        SimpleFlight flight1 = SimpleFlight.of(CLE.toString(), leg, flightSchedule, config1);


        assertTrue(seatConfigSame(flight1.seatsAvailable(econFareClass), config1));


    }



    @Test
    void testFlightHasSeats() {

    }
}
