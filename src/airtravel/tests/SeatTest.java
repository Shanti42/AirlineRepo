package airtravel.tests;

import airtravel.*;
import airtravel.tests.AirportCodes.*;
import org.junit.jupiter.api.Test;


import java.util.EnumMap;

import static airtravel.SeatClass.*;
import static airtravel.tests.AirportCodes.CLE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test SeatConfiguration and FareClass classes
 */
public class SeatTest extends FlightTest {


    /**
     * Test SeatConfiguration
     */
    /**
     * SeatConfiguration build method tests
     */
    @Test
    void testSeatConfigurationOf() {

        assertThrows(NullPointerException.class, () -> {
            SeatConfiguration.of(null);
        }, "SeatConfigOf -> Check catches null value");
        assertEquals(config1.seats(BUSINESS), 15, "Test Seats initialized and value correct");
    }

    /**
     * SeatConfiguration seats() method test
     */
    @Test
    void testSeatConfigurationSeats() {
        assertEquals(config1.seats(ECONOMY), 10, "Test value assigned and retrieved from seats");
        assertEquals(config1.seats(PREMIUM_ECONOMY), 0, "Test null values for seats return 0");
        assertEquals(config2.seats(ECONOMY), 0, "Test seat value 0 on negative");
        assertEquals(config2.seats(BUSINESS), 0, "Tests seat value 0 on 0");
    }

    /**
     * SeatConfiguration setSeats() method test
     */
    @Test
    void testSeatConfigurationSetSeats() {
        EnumMap<SeatClass, Integer> seats = new EnumMap<>(SeatClass.class);
        SeatConfiguration config = SeatConfiguration.of(seats);
        assertEquals(config.setSeats(ECONOMY, 10), 0, "Test setting seats from null Economy");
        assertEquals(config.setSeats(PREMIUM_ECONOMY, 15), 0, "Test setting seats from null Premium Economy");
        assertEquals(config.setSeats(BUSINESS, 20), 0, "Test setting seats from null Business");
        assertEquals(config.setSeats(BUSINESS, 15), 20, "Test setting value over existing");
        assertEquals(config.setSeats(BUSINESS, 1), 15, "Test setting value over existing saved");
    }

    /**
     * SeatConfiguration hasSeats() method test
     */
    @Test
    void testSeatConfigurationHasSeats() {
        EnumMap<SeatClass, Integer> seats = new EnumMap<>(SeatClass.class);
        SeatConfiguration config = SeatConfiguration.of(seats);
        assertFalse(config.hasSeats(), "Test false when seats empty");
        assertFalse(config2.hasSeats(), "Test false when lots of zeros and nulls");
        assertTrue(config1.hasSeats(), "Test true when there are seats");
    }
    /**
     *      ---  FareClass tests ---
     */
    /**
     * FareClass build method test
     */
    @Test
    void testFareClassOf(){

        assertThrows(NullPointerException.class, () -> { FareClass.of(5, null);});

        assertNotNull(FareClass.of(5, ECONOMY));
    }

    /**
     * FareClass equals() method test
     */
    @Test
    void testFareClassEquals() {
        FareClass fareClass = FareClass.of(5, ECONOMY);
        FareClass sameClass = FareClass.of(5, BUSINESS);
        FareClass diffClass = FareClass.of(1, ECONOMY);
        Integer notFareClass = new Integer(5);

        assertTrue(fareClass.equals(sameClass));
        assertFalse(fareClass.equals(diffClass));
        assertFalse(fareClass.equals(notFareClass));
    }
}
