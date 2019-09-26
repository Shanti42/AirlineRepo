package airtravel;

import java.time.Duration;
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
        RouteNode priorAirportNode = routeState.airportNode(origin);
        RouteTime bestTime = new RouteTime(null);
        while (!routeState.allReached()) {
            currentAirport = routeState.closestUnreached().getAirport();
            if (currentAirport.equals(destination)) {
                return routeState.airportNode(currentAirport);
            }
            priorAirportNode.departureTime().getTime();
            for (Flight flight : currentAirport.availableFlights(priorAirportNode.departureTime().getTime(), fareClass)) {
                if (RouteTime.of(flight.arrivalTime()).compareTo(bestTime) < 0) {
                    routeState.replaceNode(RouteNode.of(flight, priorAirportNode));
                    bestTime = RouteTime.of(flight.arrivalTime());
                }
            }
            priorAirportNode = routeState.airportNode(currentAirport);
            //removes the current airport from the unreached list.
            routeState.updateAsVisited(currentAirport);
        }
        //no route found
        return null;
    }
}
