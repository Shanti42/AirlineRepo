package airtravel;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Re-routes passengers from an airport to a final destination
 */
public final class RouteFinder {

    private final Set<Airport> airports;

    private RouteFinder(Set<Airport> airports) {
        this.airports = airports;
    }

    public static final RouteFinder of(Set<Airport> airports) {
        Objects.requireNonNull(airports, "Received null Set of Airports");

        return new RouteFinder(airports);
    }

    /**
     * Finds and returns tha last route node in the fastest route
     * from the departure aiprot to final destination
     *
     * @param origin        the departure airport
     * @param destination   the final destination
     * @param departureTime the time of departure
     * @param fareClass     the fareclass of the passenger
     * @return
     */
    public final RouteNode route(Airport origin, Airport destination, LocalTime departureTime, FareClass fareClass) {
        RouteState routeState = RouteState.of(airports, origin, departureTime);
        Airport currentAirport;
        RouteNode tempRouteNode;
        RouteNode priorAirportNode = routeState.airportNode(origin);
        while (!routeState.allReached()) {
            currentAirport = routeState.closestUnreached().getAirport();
            if (currentAirport.equals(destination)) {
                return routeState.airportNode(currentAirport);
            }
            for (Flight flight : currentAirport.availableFlightsFromRoute(routeState.airportNode(currentAirport), fareClass)) {
                tempRouteNode = routeState.airportNode(flight.destination());
                if (flight.arrivalTime().compareTo(tempRouteNode.getArrivalTime().getTime()) > 1) {
                    routeState.replaceNode(RouteNode.of(flight, priorAirportNode));
                }
            }
            priorAirportNode = routeState.airportNode(currentAirport);
            routeState.updateAsVisited(currentAirport);

        }
        //no route found
        return null;
    }
}
