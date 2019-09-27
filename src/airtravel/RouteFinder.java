package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import static airtravel.RouteTime.UNKNOWN;

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
        //check for null values
        Objects.requireNonNull(origin, "RouteFinder, route() -> origin null");
        Objects.requireNonNull(destination, "RouteFinder, route() -> destination null");
        Objects.requireNonNull(departureTime, "RouteFinder route() -> departureTime null");
        Objects.requireNonNull(fareClass, "RouteFinder route() -> fareClass null");

        RouteState routeState = RouteState.of(airports, origin, departureTime);

        RouteNode currentAirportNode;
        while (!routeState.allReached()) {
            currentAirportNode = routeState.closestUnreached();
            if (currentAirportNode.getAirport().equals(destination)) {
                return currentAirportNode;
            }
            for (Flight flight : currentAirportNode.availableFlights(fareClass)) {
                RouteTime destinationArrivalTime = routeState.airportNode(flight.destination()).getArrivalTime();

                if (RouteTime.of(flight.arrivalTime()).compareTo(destinationArrivalTime) < 0) {
                    routeState.replaceNode(RouteNode.of(flight, currentAirportNode));
                }
            }
        }
        //no route found
        return null;
    }


}
