package airtravel;

import java.time.LocalTime;
import java.util.Set;

/**
 * Re-routes passengers from an airport to a final destination
 */
public final class RouteFinder {

    private final Set<Airport> airports;

    private RouteFinder(Set<Airport> airports){
        this.airports = airports;
    }

    /**
     * Finds and returns tha last route node in the fastest route
     * from the departure aiprot to final destination
     * @param origin the departure airport
     * @param destination the final destination
     * @param departureTime the time of departure
     * @param fareClass the fareclass of the passenger
     * @return
     */
    public final RouteNode route(Airport origin, Airport destination, LocalTime departureTime, FareClass fareClass){

        return null;
    }
}
