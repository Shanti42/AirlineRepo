package airtravel.tests;
import static airtravel.SeatClass.*;

import airtravel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Tests for the RouteNode, RouteState, RouteTime, and RouteFinder classes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteTest  {


    public Airport airport1;
    public Airport airport2;
    public Airport airport3;
    public Airport airport4;

    public RouteTime routeTime1;
    public RouteTime routeTime2;
    public RouteTime routeTime3;
    public RouteTime routeUnknown;

    public Leg leg;
    public FlightSchedule flightSchedule;
    public SeatConfiguration seatConfig;
    public Flight flight;

    public Set<Airport> airports = new HashSet<>();

    @BeforeAll
    void initializeRoutes() {
        airport1 = Airport.of("CLE",Duration.ofHours(5));
        airport2 = Airport.of("CLE",Duration.ofHours(7));
        airport3 = Airport.of("CLE",Duration.ofHours(3));
        airport4 = Airport.of("LGA",Duration.ofHours(1));

        routeTime1 = new RouteTime(LocalTime.of(6,0));
        routeTime2 = new RouteTime(LocalTime.of(5,0));
        routeTime3 = new RouteTime(LocalTime.of(3,0));
        routeUnknown = new RouteTime(null);

        leg = Leg.of(airport1, Airport.of("LGA", Duration.ofHours(6)));
        flightSchedule = FlightSchedule.of(LocalTime.MIN,LocalTime.NOON);

        seatConfig = SeatConfiguration.of(new EnumMap<SeatClass,Integer>(SeatClass.class));
        seatConfig.setSeats(BUSINESS, 10);
        seatConfig.setSeats(ECONOMY, 20);
        seatConfig.setSeats(PREMIUM_ECONOMY, 5);

        flight = SimpleFlight.of("A1102", leg, flightSchedule, seatConfig);

        airports.add(airport1);
        airports.add(airport4);
    }

    /**
     *      --- RouteTime Tests ---
     */
    /**
     * RouteTime isKnown() method test
     */
    @Test
    void testRouteTimeIsKnown() {
        RouteTime routeKnown = new RouteTime(LocalTime.NOON);

        assertFalse(routeUnknown.isKnown());
        assertTrue(routeKnown.isKnown());
    }

    /**
     * RouteTime plus() method tests
     */
    @Test
    void testRouteTimePlus() {
        RouteTime routeOfFive = new RouteTime(LocalTime.of(5,0));

        Duration durationOne = Duration.ofHours(1);
        Duration durationThree = Duration.ofHours(3);
        Duration durationTen = Duration.ofHours(10);

        RouteTime routeOfSix = new RouteTime(LocalTime.of(6,0));
        RouteTime routeOfEight = new RouteTime(LocalTime.of(8,0));
        RouteTime routeOfFifteen = new RouteTime(LocalTime.of(15,0));

        assertEquals(RouteTime.UNKNOWN,routeUnknown.plus(durationOne));
        assertNotNull(routeOfFive.plus(durationOne));
        assertTrue(routeOfSix.compareTo(routeOfFive.plus(durationOne)) ==0);
        assertTrue(routeOfEight.compareTo(routeOfFive.plus(durationThree)) ==0);
        assertTrue(routeOfFifteen.compareTo(routeOfFive.plus(durationTen)) ==0);

    }

    /**
     * RouteTime compareTo method tests
     */
    @Test
    void testRouteTimeCompareTo() {

        assertTrue(routeTime1.compareTo(routeTime1) == 0); //Compare RouteTime 6 to 6
        assertFalse(routeTime1.compareTo(routeTime2) == 0); //Compare RouteTime 6 to 6
        assertTrue( routeTime1.compareTo(routeTime2) > 0); //Compare RouteTime 6 to 5
        assertTrue(routeTime3.compareTo(routeTime1) < 0); //Compare RouteTime 3 to 6
    }

    /**
     * RouteNode Tests
     */
    /**
     * Tests for all three of RouteNode's build methods
     */
    @Test
    void testRouteNodeBuildMethods() {

        RouteNode routeNode1 = RouteNode.of(airport1, routeTime1, null);
        assertNotNull(routeNode1);
        assertNotNull(RouteNode.of(airport2, routeTime2, routeNode1));
        assertNotNull(RouteNode.of(flight,routeNode1));
        assertNotNull(RouteNode.of(airport3));

        assertThrows(NullPointerException.class, () -> RouteNode.of(null,routeTime1,null));
        assertThrows(NullPointerException.class, () -> RouteNode.of(airport4 ,null,null));
        assertThrows(NullPointerException.class, () -> RouteNode.of(null ,routeNode1));
        assertThrows(NullPointerException.class, () -> RouteNode.of(null));

    }

    /**
     * RouteNode isArrivalTimeKnown() method
     */
    @Test
    void testRouteNodeIsArrivalTimeKnown() {
        RouteNode knownArrival = RouteNode.of(airport1, routeTime1,  null);
        RouteNode unknownArrival = RouteNode.of(airport1, RouteTime.UNKNOWN, null);
        assertTrue(knownArrival.isArrivalTimeKnown());
        assertFalse(unknownArrival.isArrivalTimeKnown());
    }

    /**
     * RouteNode departureTime() method
     */
    @Test
    void testRouteNodeDepartureTime() {
        //airport1 has a connection time of 5 and arrival time is 6. Returned RouteTime should be 11
        RouteNode routeNode = RouteNode.of(airport1, routeTime1, null);

        RouteTime expectedRouteTime = new RouteTime(LocalTime.of(11,0));
        RouteTime computedRouteTime = routeTime1.plus(Duration.ofHours(5));
        assertTrue(expectedRouteTime.compareTo(routeNode.departureTime()) ==0);
        assertTrue(computedRouteTime.compareTo(routeNode.departureTime()) ==0);
    }

    /**
     * RouteNode availableFlights method test
     */
    @Test
    void testRouteNodeAvailableFlights() {

        Airport origin = Airport.of("CLE",Duration.ofHours(3));
        Airport dest1 = Airport.of("LGA",Duration.ofHours(5));
        Airport dest2 = Airport.of("LAX",Duration.ofHours(2));

        Leg leg1 = Leg.of(origin,dest1);
        Leg leg2 = Leg.of(origin, dest2);

        LocalTime depart1 = LocalTime.of(5,0);
        LocalTime depart2 = LocalTime.of(6,0);
        LocalTime arrive1 = LocalTime.of(7,0);
        LocalTime arrive2 = LocalTime.of(8,0);
        FlightSchedule schedule1 = FlightSchedule.of(depart1,arrive1);
        FlightSchedule schedule2 = FlightSchedule.of(depart2,arrive2);

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 10);
        seatConfig.setSeats(BUSINESS, 20);

        Flight flight1 = SimpleFlight.of("A101",leg1,schedule1,seatConfig);
        Flight flight2 = SimpleFlight.of("B101",leg2,schedule2,seatConfig);

        origin.addFlight(flight1);
        origin.addFlight(flight2);
        RouteNode routeNode = RouteNode.of(origin, new RouteTime(arrive1), null);
        FareClass fareClass = FareClass.of(4, BUSINESS);

        Set<Flight> flightSet = routeNode.getAirport().availableFlights(depart1,fareClass);
        //System.out.println(flightSet.isEmpty());
        //System.out.println(flightSet.toString());
        assertTrue(flightSet.size() == 2);

    }

    /**
     * RouteNode compareTo() method test
     */
    @Test
    void testRouteNodeCompareTo() {
        RouteNode routeNode1  = RouteNode.of(airport1, routeTime1, null);
        RouteNode routeNode2 = RouteNode.of(airport2, routeTime2, routeNode1);
        RouteNode routeNode3 = RouteNode.of(airport4, routeTime1, routeNode2);

        assertTrue(routeNode1.compareTo(routeNode1) == 0); //Compare RouteTime 6 to 6
        assertTrue(routeNode1.compareTo(routeNode2) > 0); //Compare RouteTime 6 to 5
        assertTrue(routeNode1.compareTo(routeNode3) < 0); //RouteTimes are the same so compares 'CLE' to 'LGA'

    }

    /**
     * RouteFinder Tests
     */
    /**
     * RouteFinder build method tests
     */
    @Test
    void testRouteFinderOf() {
        assertThrows(NullPointerException.class, () -> RouteFinder.of(null));
        assertNotNull(RouteFinder.of(airports));
    }

    /**
     * RouteFinder route() method test #1 - Asserts the predicted output using a connected graph
     */
    @Test
    void testRouteFinderRouteConnectedGraph() {
        //Connected Graph
        Airport origin = Airport.of("CLE", Duration.ofHours(1));
        Airport destLGA = Airport.of("LGA", Duration.ofHours(1));
        Airport destMIA = Airport.of("MIA", Duration.ofHours(1));
        Airport destLAX = Airport.of("LAX", Duration.ofHours(1));

        //Legs of flight from CLE to NY to LA
        Leg leg1 = Leg.of(origin, destLGA);
        Leg leg2 = Leg.of(destLGA, destLAX);

        //Legs of flight from CLE to Miami to LA
        Leg leg3 = Leg.of(origin, destMIA);
        Leg leg4 = Leg.of(destMIA, destLAX);

        //Flight Schedule for CLE to NY to LA
        FlightSchedule schedule1 = FlightSchedule.of(LocalTime.of(3,0), LocalTime.of(4,0)); //1 Hour Flight
        FlightSchedule schedule2 = FlightSchedule.of(LocalTime.of(5,0), LocalTime.of(8,0)); //3 Hours Flight

        //Flight Schedule for CLE to Miami to LA
        FlightSchedule schedule3 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(4, 0)); //2 Hour Flight
        FlightSchedule schedule4 = FlightSchedule.of(LocalTime.of(5,0), LocalTime.of(8,0)); //3 Hour Flight

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 19);
        FareClass fareClass = FareClass.of(4, ECONOMY);

        Flight flight1 = SimpleFlight.of("A101", leg1, schedule1, seatConfig);
        Flight flight2 = SimpleFlight.of("B101", leg2, schedule2, seatConfig);
        Flight flight3 = SimpleFlight.of("A102", leg3, schedule3, seatConfig);
        Flight flight4 = SimpleFlight.of("C101", leg4, schedule4, seatConfig);

        origin.addFlight(flight1);
        origin.addFlight(flight3);

        destLGA.addFlight(flight2);
        destMIA.addFlight(flight4);

        Set<Airport> airports = new HashSet<>();
        airports.add(origin);
        airports.add(destLGA);
        airports.add(destMIA);
        airports.add(destLAX);

        RouteFinder routeFinder = RouteFinder.of(airports);

        //Departure Time = FlightSchedule departTime + origin airports connectionTimeMin
        // I.e. Minimum Departure Time is 2:00 + 100 = 3:00
        assertEquals(destLAX.getCode(), routeFinder.route(origin, destLAX, LocalTime.of(2,0), fareClass).getAirport().getCode());

    }

    /**
     * RouteFinder route() method test #2 - Test using a disconnected graph
     */
    @Test
    void testRouteFinderRouteDisconnectedGraph() {
        //Connected Graph
        Airport origin = Airport.of("CLE", Duration.ofHours(1));
        Airport destLGA = Airport.of("LGA", Duration.ofHours(1));
        Airport destLAX = Airport.of("LAX", Duration.ofHours(1));
        Airport destMIA = Airport.of("MIA", Duration.ofHours(1));

        //Legs of flight from CLE to NY to LA
        Leg leg1 = Leg.of(origin, destLGA);
        Leg leg2 = Leg.of(destLGA, destLAX);

        //Legs of flight from CLE to Miami
        Leg leg3 = Leg.of(origin, destMIA);

        //Flight Schedule for CLE to NY to LA
        FlightSchedule schedule1 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(3,0)); //1 Hour Flight
        FlightSchedule schedule2 = FlightSchedule.of(LocalTime.of(5,0), LocalTime.of(8,0)); //3 Hours Flight

        //Flight Schedule for CLE to Miami
        FlightSchedule schedule3 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(4, 0)); //2 Hour Flight

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 19);
        FareClass fareClass = FareClass.of(4, ECONOMY);

        Flight flight1 = SimpleFlight.of("A101", leg1, schedule1, seatConfig);
        Flight flight2 = SimpleFlight.of("B101", leg2, schedule2, seatConfig);
        Flight flight3 = SimpleFlight.of("A102", leg3, schedule3, seatConfig);

        origin.addFlight(flight1);
        origin.addFlight(flight3);

        destLGA.addFlight(flight2);

        Set<Airport> airports = new HashSet<>();
        airports.add(origin);
        airports.add(destLAX);
        airports.add(destLGA);
        airports.add(destMIA);

        RouteFinder routeFinder = RouteFinder.of(airports);
        assertNull(routeFinder.route(origin, destMIA, LocalTime.of(3,0), fareClass));
    }


}
