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
     *   SimpleFlight build method
     */
    @Test
    void testSimpleFlightOf(){


        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,null, null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,null,null, seatConfig);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,null,null, null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,null,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,null,flightSchedule,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode,leg,null,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(originCode, null,flightSchedule,null);});
        Assertions.assertThrows(NullPointerException.class, () -> {SimpleFlight.of(null,leg,flightSchedule,null);});

    }
    /**
     * SimpleFlight - Test if isShort() throws exception when expected and returns a boolean otherwise. Further testing is provided in FlightSchedule method
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

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleFlight.of("Code", Leg.of(Airport.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        FlightGroup flightGroup = FlightGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {flightGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {flightGroup.add(badOriginFlight);});
        Assertions.assertTrue(flightGroup.add(flight));
        Assertions.assertFalse(flightGroup.add(flight));

    }

    @Test
    void testFlightGroupRemove() {

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleFlight.of("Code", Leg.of(Airport.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        FlightGroup flightGroup = FlightGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {flightGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {flightGroup.add(badOriginFlight);});
        Assertions.assertFalse(flightGroup.remove(flight));
        flightGroup.add(flight);
        Assertions.assertTrue(flightGroup.remove(flight));
        assertFalse(flightGroup.remove(flight));

    }

    @Test
    void testFlightGroupFlightsAtOrAfter() {

        FlightGroup flightGroup = FlightGroup.of(Airport.of("LGA",Duration.ofHours(1)));

        Assertions.assertNotNull(flightGroup.flightsAtOrAfter(depart));
        Assertions.assertEquals(new HashSet<>(), flightGroup.flightsAtOrAfter(LocalTime.MIDNIGHT));
    }

    @Test
    void testSimpleFlightSeatsAvailable() {

        assertThrows(NullPointerException.class, () -> {
            flight.seatsAvailable(null);
        }, "check catches null fare class");
        assertTrue(seatConfigSame(flight1.seatsAvailable(econFareClass), config1), "Check returns proper SeatConfiguration");
    }


    @Test
    void testSimpleAndAbstractFlightHasSeats(){
        assertThrows(NullPointerException.class, () -> {
            flight.hasSeats(null);
        }, "check catches null fare class");
        assertTrue(flight1.hasSeats(econFareClass), "Test there are seats for fare class");
        assertTrue(flight1.hasSeats(premFareClass), "There are seats");
        assertFalse(flight2.hasSeats(premFareClass), "Test no seats available");
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

    void printSeatConfig(SeatConfiguration seatConfig){
        for(SeatClass seatClass: SeatClass.values()) {
            System.out.println(seatConfig.seats(seatClass));
        }
    }


}
