package airtravel.tests;
import static airtravel.SeatClass.*;

import airtravel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;

public class RouteTest  {


    public Airport airport1;
    public Airport airport2;
    public Airport airport3;
    public Airport airport4;

    @BeforeAll
    void initializeRoutes() {
        airport1 = Airport.of("CLE",Duration.ofHours(5));
        airport2 = Airport.of("CLE",Duration.ofHours(7));
        airport3 = Airport.of("CLE",Duration.ofHours(3));
        airport4 = Airport.of("CLE",Duration.ofHours(1));

    }
    /**
     * RouteNode Tests
     */
    @Test
    void testRouteNodeBuildMethods() {
        Airport airport = Airport.of("CLE",Duration.ofHours(5));
        Airport airport2 = Airport.of("CLE",Duration.ofHours(10));
        RouteTime arrivalTime = new RouteTime(LocalTime.of(6,0));

        RouteNode routeNode1 = RouteNode.of(airport, arrivalTime, null);
        RouteNode routeNode2 = RouteNode.of(airport, arrivalTime)
    }

    @Test
    void testRouteNodeIsArrivalTimeKnown() {

    }

    @Test
    void testRouteNodeDepartureTime() {

    }

    @Test
    void testRouteNodeAvailableFlights() {

    }

    @Test
    void testRouteNodeCompareTo() {

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
