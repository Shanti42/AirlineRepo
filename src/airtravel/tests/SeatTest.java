package airtravel.tests;
import airtravel.SeatClass;
import airtravel.SeatConfiguration;
import org.junit.jupiter.api.Test;

import java.lang.management.BufferPoolMXBean;
import java.util.EnumMap;
import java.util.function.Predicate;

import static airtravel.SeatClass.*;
import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {
    EnumMap<SeatClass, Integer> seats1 = new EnumMap<>(SeatClass.class);
    EnumMap<SeatClass, Integer> seats2 = new EnumMap<>(SeatClass.class);
    SeatConfiguration config1;
    SeatConfiguration config2;

    public SeatTest(){
        seats1.put(ECONOMY, 10);
        seats1.put(BUSINESS, 15);
        seats2.put(ECONOMY, -2);
        seats2.put(BUSINESS, 0);
        config1 = SeatConfiguration.of(seats1);
        config2 = SeatConfiguration.of(seats2);
    }

    /**
     * Test SeatConfiguration
     */
    @Test
    void testSeatConfigurationOf(){
        //checking null check
        assertThrows(NullPointerException.class, () -> {
            SeatConfiguration.of(null);
        });
        assertEquals(config1.seats(BUSINESS), 15, "Test Seats initialized and value correct");
    }

    @Test
    void testSeats(){
        assertEquals(config1.seats(ECONOMY), 10, "Test value assigned and retrieved from seats");
        assertEquals(config1.seats(PREMIUM_ECONOMY), 0, "Test null values for seats return 0");
        assertEquals(config2.seats(ECONOMY), 0, "Test seat value 0 on negative");
        assertEquals(config2.seats(BUSINESS), 0, "Tests seat value 0 on 0");
    }

    @Test
    void testSetSeats() {
        EnumMap<SeatClass, Integer> seats = new EnumMap<>(SeatClass.class);
        SeatConfiguration config = SeatConfiguration.of(seats);
        assertEquals(config.setSeats(ECONOMY, 10), 0, "Test setting seats from null Economy");
        assertEquals(config.setSeats(PREMIUM_ECONOMY, 15), 0, "Test setting seats from null Premium Economy");
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
}
