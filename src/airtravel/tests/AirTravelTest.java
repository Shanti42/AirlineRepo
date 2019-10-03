package airtravel.tests;

import airtravel.Airport;

import airtravel.Flight;
import airtravel.Leg;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static airtravel.tests.AirportCodes.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Airport and Leg classes
 */
class AirTravelTest extends FlightTest{

    /**
     *  --- Airport Tests ---
     */
    /**
     * Airport build method tests
     */
    @Test
    void testAirportOf() {

        //test error handling for method
        assertThrows(NullPointerException.class, () -> { Airport.of(null, Duration.ofHours(2)); });
        assertThrows(NullPointerException.class, () -> { Airport.of(CLE.name(), null); });
        assertThrows(NullPointerException.class, () -> { Airport.of(null, null); });
        Airport airport = Airport.of("CLE", Duration.ofHours(1));
        assertEquals(Airport.of(CLE.name(), Duration.ofHours(1)), airport, "Test build method creates valid object");
    }

    /**
     * Airport equals() method test
     */
    @Test
    void testAirportEquals() {

        Airport airport = Airport.of("CLE", Duration.ofHours(5));
        Airport sameAirport = Airport.of("CLE", Duration.ofHours(5));
        Airport differentAirport = Airport.of("LGA", Duration.ofHours(5));
        Integer notAirport = new Integer(1);

        assertTrue(airport.equals(sameAirport), "Test two equal airports");
        assertFalse(airport.equals(differentAirport), "Test two non-equals airports");
        assertFalse(airport.equals(notAirport), "Test with non airport object");
    }

    /**
     * Airport compareTo() method test
     */
    @Test
    void testCompareTo() {

        Airport airport = Airport.of("CLE", Duration.ofHours(5));
        Airport sameAirport = Airport.of("CLE", Duration.ofHours(5));
        Airport differentAirport = Airport.of("LGA", Duration.ofHours(5));

        assertTrue(airport.compareTo(differentAirport) < 0, "Test less than");
        assertTrue(differentAirport.compareTo(airport) > 0, "Test greater than");
        assertTrue(airport.compareTo(sameAirport) == 0, "Test equal to");
    }

    /**
     * Airport availableFlights() method test
     */
    @Test
    void testAirportAvailableFlights() {
        Set<Flight> flights = new HashSet<Flight>();
        flights.add(flight);
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);

        assertEquals(flights.toString(), origin.availableFlights(LocalTime.MIN, econFareClass).toString());
    }

    /**
     *      --- Leg Tests ---
     */
    /**
     * Leg build method test
     */
    @Test
    void testLegOf(){

        Airport origin = Airport.of("CLE", Duration.ofHours(5));
        Airport dest = Airport.of("LGA", Duration.ofHours(1));

        assertThrows(NullPointerException.class, () -> { Leg.of(null, dest); });
        assertThrows(NullPointerException.class, () -> { Leg.of(origin, null); });
        assertThrows(NullPointerException.class, () -> { Leg.of(null, null); });

        Leg leg1 = Leg.of(origin, dest);
        assertTrue(leg1.getOrigin().equals(origin), "Test Origin added to Leg");
        assertTrue(leg1.getDestination().equals(dest), "Test Destination added to Leg");
    }

}