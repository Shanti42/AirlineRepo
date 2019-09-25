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
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteTest  {


    public Airport airport1;
    public Airport airport2;
    public Airport airport3;
    public Airport airport4;

    public RouteTime routeTime1;
    public RouteTime routeTime2;

    public Leg leg;
    public FlightSchedule flightSchedule;
    public SeatConfiguration seatConfig;
    public Flight flight;

    public Set<Airport> airports;

    @BeforeAll
    void initializeRoutes() {
        airport1 = Airport.of("CLE",Duration.ofHours(5));
        airport2 = Airport.of("CLE",Duration.ofHours(7));
        airport3 = Airport.of("CLE",Duration.ofHours(3));
        airport4 = Airport.of("LGA",Duration.ofHours(1));

        routeTime1 = new RouteTime(LocalTime.of(6,0));
        routeTime2 = new RouteTime(LocalTime.of(5,0));

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
     * RouteNode Tests
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

    @Test
    void testRouteNodeIsArrivalTimeKnown() {
        RouteNode knownArrival = RouteNode.of(airport1, routeTime1,  null);
        RouteNode unknownArrival = RouteNode.of(airport1, RouteTime.UNKNOWN, null);
        assertTrue(knownArrival.isArrivalTimeKnown());
        assertFalse(unknownArrival.isArrivalTimeKnown());
    }

    @Test
    void testRouteNodeDepartureTime() {
        //airport1 has a connection time of 5 and arrival time is 6. Returned RouteTime should be 11
        RouteNode routeNode = RouteNode.of(airport1, routeTime1, null);

        RouteTime expectedRouteTime = new RouteTime(LocalTime.of(11,0));
        RouteTime computedRouteTime = routeTime1.plus(Duration.ofHours(5));
        assertTrue(expectedRouteTime.compareTo(routeNode.departureTime()) ==0);
        assertTrue(computedRouteTime.compareTo(routeNode.departureTime()) ==0);
    }

    @Test
    void testRouteNodeAvailableFlights() {

        RouteNode routeNode = RouteNode.of(airport1, routeTime1, null);
        FareClass fareClass = FareClass.of(4, BUSINESS);
        Set<Flight> flights = routeNode.availableFlights(fareClass);

    }

    @Test
    void testRouteNodeCompareTo() {
        RouteNode routeNode1  = RouteNode.of(airport1, routeTime1, null);
        RouteNode routeNode2 = RouteNode.of(airport2, routeTime2, routeNode1);
        RouteNode routeNode3 = RouteNode.of(airport3, routeTime1, routeNode2);

        assertTrue(routeNode1.compareTo(routeNode1) == 0); //Compare RouteTime 6 to 6
        assertTrue(routeNode1.compareTo(routeNode2) >= 0);
        assertTrue(routeNode1.compareTo(routeNode3) <= 0);

    }

    /**
     * Test RouteState
     */
    @Test
    void testRouteStateOf() {

    }

    @Test
    void testRouteStateReplaceNode() {

    }

    @Test
    void testRouteStateClosestUnreached() {

    }

    /**
     * RouteFinder
     */

    @Test
    void testRouteFinderRoute() {

    }

}
