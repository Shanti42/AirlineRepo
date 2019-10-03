package airtravel.tests;

import airtravel.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalTime;
import java.time.Duration;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static airtravel.SeatClass.*;
import static airtravel.tests.AirportCodes.CLE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Flight interface, and the AbstractFlight and SimpleFlight classes
 * Tests FlightGroup and FlightSchedule classes
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

    public static EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);

    public static Leg leg;
    public static FlightSchedule flightSchedule;
    public static SeatConfiguration seatConfig;
    public static Flight flight;

    public EnumMap<SeatClass, Integer> seats1 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats2 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats3 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats4 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats3Limited = new EnumMap<>(SeatClass.class);

    public SeatConfiguration config1;
    public SeatConfiguration config2;
    public SeatConfiguration config3;
    public SeatConfiguration config4;


    public static FareClass econFareClass = FareClass.of(3, ECONOMY);
    public static FareClass busnFareClass = FareClass.of(7, BUSINESS);
    public static FareClass busnFareClassLow = FareClass.of(7, BUSINESS);
    public static FareClass premFareClass = FareClass.of(8, PREMIUM_ECONOMY);


    SimpleFlight flight1;
    SimpleFlight flight2;
    Flight flight3;
    Flight flight4;

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

        map.put(BUSINESS, 10);
        map.put(ECONOMY, 20);
        seatConfig = SeatConfiguration.of(map);
        flight = SimpleFlight.of(flightCode, leg, flightSchedule, seatConfig);

        seats1.put(ECONOMY, 10);
        seats1.put(BUSINESS, 15);
        seats2.put(ECONOMY, -2);
        seats2.put(BUSINESS, 0);
        config1 = SeatConfiguration.of(seats1);
        config2 = SeatConfiguration.of(seats2);

        seats3.put(ECONOMY, 2);
        seats3.put(BUSINESS, 15);
        seats3.put(PREMIUM_ECONOMY, 8);
        seats4.put(ECONOMY, -2);
        seats4.put(BUSINESS, 0);
        seats4.put(PREMIUM_ECONOMY, 4);

        config3 = SeatConfiguration.of(seats3);
        config4 = SeatConfiguration.of(seats4);


        flight1 = SimpleFlight.of(CLE.toString(), leg, flightSchedule, config1);
        flight2 = SimpleFlight.of(CLE.toString(), leg, flightSchedule, config2);
        flight3 = SimpleFlight.of(CLE.toString(), leg, flightSchedule, config3);
        flight4 = SimpleFlight.of(CLE.toString(), leg, flightSchedule, config4);
    }

    /**
     * Helper routine Returns whether both SeatConfigurations are the same
     */
    boolean seatConfigSame(SeatConfiguration config1, SeatConfiguration config2){
        boolean isSame = true;
        No_Match:
        for(SeatClass seatClass: SeatClass.values()){
            if(config1.seats(seatClass) != config2.seats(seatClass)){
                isSame = false;
                break No_Match;
            }
        }
        return isSame;
    }
    /**
     *      ---   AbstractFlights and SimpleFlight Tests ---
     */
    /**
     *   SimpleFlight build method test - Tests exception handling for build method.
     *   Tests SimpleFlight build method functionality
     */
    @Test
    void testSimpleFlightOf(){

        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,null, null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,null,null, seatConfig);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,null,null, null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,null,null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,flightSchedule,null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,leg,null,null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode, null,flightSchedule,null);});
        assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,flightSchedule,null);});

        assertNotNull(SimpleFlight.of(originCode, leg, flightSchedule, seatConfig));

    }

    /**
     * AbstractFlight isShort() method test - Tests exception handling and functionality
     * Further testing is provided in FlightSchedule isShort() test
     */
    @Test
    void testAbstractFlightIsShort() {
        Duration nullDuration = null;
        Duration duration = Duration.ofHours(10);

        assertThrows(NullPointerException.class, () -> {flight.isShort(nullDuration);});
        assertNotNull(flight.isShort(duration));
    }

    /**
     * AbstractFlight hasSeats() method test - Tests exception handling and functionality
     * Further testing is provided is SeatConfiguration hasSeat() test
     */
    @Test
    void testAbstractFlightHasSeats() {

        assertThrows(NullPointerException.class, () -> {flight.hasSeats(null);});
        assertNotNull((flight.hasSeats(premFareClass)));
    }

    /**
     *      --- FlightGroup Tests ---
     */

    /**
     * FlightGroup build method test - Tests exception handling for build method.
     * Tests FlightGroup build method functionality
     */
    @Test
    void testFlightGroupOf(){

        FlightGroup flightGroup = FlightGroup.of(origin);
        Assertions.assertThrows(NullPointerException.class, () -> {FlightGroup.of(null);});
        assertNotNull(FlightGroup.of(origin));
    }

    /**
     * FlightGroup add() method
     */
    @Test
    void testFlightGroupAdd(){

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleFlight.of("Code", Leg.of(Airport.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        FlightGroup flightGroup = FlightGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {flightGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {flightGroup.add(badOriginFlight);});

        assertTrue(flightGroup.add(flight));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight);
        assertEquals(flights, flightGroup.flightsAtOrAfter(flight.departureTime()));

        assertFalse(flightGroup.add(flight));

    }

    /**
     * FlightGroup remove() method
     */
    @Test
    void testFlightGroupRemove() {

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleFlight.of("Code", Leg.of(Airport.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        FlightGroup flightGroup = FlightGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {flightGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {flightGroup.add(badOriginFlight);});

        flightGroup.add(flight);
        flightGroup.add(flight1);

        assertTrue(flightGroup.remove(flight));
        assertFalse(flightGroup.remove(flight));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight1);

        assertEquals(flights, flightGroup.flightsAtOrAfter(flight1.departureTime()));

    }

    /**
     * FlightGroup flightsAtOrAfter() test
     */
    @Test
    void testFlightGroupFlightsAtOrAfter() {

        FlightGroup flightGroup = FlightGroup.of(Airport.of("CLE",Duration.ofHours(1)));

        flightGroup.add(flight);
        flightGroup.add(flight1);
        flightGroup.add(flight2);
        assertNotNull(flightGroup.flightsAtOrAfter(flight.departureTime()));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight);
        flights.add(flight1);
        flights.add(flight2);
        assertEquals(flights, flightGroup.flightsAtOrAfter(flight.departureTime()));

        assertEquals(new HashSet<>(), flightGroup.flightsAtOrAfter(LocalTime.of(20,0)));
    }

    /**
     *      ---- FlightSchedule tests ---
     */
    /**
     * FlightSchedule build method test
     */
    @Test
    void testFlightScheduleOf() {

        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(null, null); });
        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(depart, null); });
        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(null, arrival); });

        assertNotNull(FlightSchedule.of(depart, arrival));
    }

    /**
     * FlightSchedule isShort() test
     */
    @Test
    void testFlightScheduleIsShort() {

        Duration oneHour = Duration.ofHours(1);
        Duration twoHour = Duration.ofHours(2);
        Duration min30 = Duration.ofMinutes(30);

        LocalTime oneHourLoc = LocalTime.of(1, 0);
        LocalTime min30Loc = LocalTime.of(0, 30);
        LocalTime twoHourLoc = LocalTime.of(2, 0);

        FlightSchedule hourFlight = FlightSchedule.of(oneHourLoc, twoHourLoc);

        assertThrows(NullPointerException.class, () -> { hourFlight.isShort(null); });
        assertTrue(hourFlight.isShort(oneHour), "Duration equal to flight");
        assertFalse(hourFlight.isShort(min30), "Duration shorter than flight");
        assertTrue(hourFlight.isShort(twoHour), "Duration longer than flight");
    }

}
