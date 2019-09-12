package airtravel.tests;

import airtravel.Airport;
import airtravel.Leg;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static airtravel.tests.AirportCodes.*;
import static org.junit.jupiter.api.Assertions.*;

class AirTravelTest {


    Duration hour1 = Duration.ofHours(1);
    Duration hour2 = Duration.ofHours(2);
    Duration hour3 = Duration.ofHours(3);

    Object notAirport = new Object();
    Airport a1 = Airport.of(CLE.name(), hour1);
    Airport a2 = Airport.of(LAS.name(), hour2);
    Airport a3 = Airport.of(CLE.name(), hour2);
    Airport a4 = Airport.of(CLE.name(), hour1);
    Airport a5 = Airport.of(GRE.name(), hour3);


    /**
     * Test methods in Airport class
     */

    @Test
    void testAirportOf() {
        //test error handling for method
        assertThrows(NullPointerException.class, () -> {
            Airport test = Airport.of(null, hour2);
        });
        assertThrows(NullPointerException.class, () -> {
            Airport test = Airport.of(CLE.name(), null);
        });
        assertThrows(NullPointerException.class, () -> {
            Airport test = Airport.of(null, null);
        });
        assertEquals(Airport.of(CLE.name(), hour1), a1, "Test build method creates valid object");
    }


    @Test
    void testEquals() {
        assertTrue(a1.equals(a4), "Test two equal airports");
        assertFalse(a1.equals(notAirport), "Test method catches non-airport classes");
        assertTrue(a1.equals(a3), "Test two equal airports");
        assertFalse(a1.equals(null), "Test false on null object");
    }


    @Test
    void testCompareTo() {
        assertTrue(a1.compareTo(a2) < 0, "Test less than");
        assertTrue(a2.compareTo(a5) > 0, "Test greater than");
        assertTrue(a1.compareTo(a3) == 0, "Test equal to");
    }

    /**
     * Test methods in Leg class
     */


    @Test
    void testLegOf(){
        assertThrows(NullPointerException.class, () -> {
            Leg test = Leg.of(null, a1);
        });
        assertThrows(NullPointerException.class, () -> {
            Leg test = Leg.of(a2, null);
        });
        assertThrows(NullPointerException.class, () -> {
            Leg test = Leg.of(null, null);
        });
        Leg leg1 = Leg.of(a1, a2);
        assertTrue(leg1.getOrigin().equals(a1), "Test Origin added to Leg");
        assertTrue(leg1.getDestination().equals(a2), "Test Destination added to Leg");


    }



}