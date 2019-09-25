package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The Airport on a route, including the identification code and the connection time of the airport
 */
public final class Airport implements Comparable<Airport> {

    // short string identifier for the Airport
    private final String code;
    // the shortest length of time for a passenger to transfer planes
    private final Duration connectionTimeMin;

    //New additions based on FlightGroup section of Assignment
    private final FlightGroup outFlights = FlightGroup.of(this);

    private Airport(String code, Duration connectionTimeMin) {
        this.code = code;
        this.connectionTimeMin = connectionTimeMin;
    }

    public static final Airport of(String code, Duration connectionTimeMin) {
        Objects.requireNonNull(code, "Airport identifier code is null");
        Objects.requireNonNull(connectionTimeMin, "Connection time parameter is null");
        return new Airport(code, connectionTimeMin);
    }

    public String getCode() {
        return code;
    }

    public Duration getConnectionTimeMin() {
        return connectionTimeMin;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Airport) {
            Airport otherAirport = (Airport) object;
            return getCode().equals(otherAirport.code);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

    @Override
    public int compareTo(Airport otherAirport) {
        return getCode().compareTo(otherAirport.getCode());
    }

    @Override
    public String toString() {
        return getCode();
    }

    public final boolean addFlight(Flight flight) {
        return outFlights.add(flight);
    }

    public final boolean removeFlight(Flight flight) {
        return outFlights.remove(flight);
    }

    /**
     * Finds flights that leave at or after the departure time that have seats for the fare class
     * @param departureTime the time flights must leave at or after
     * @param fareclass the fare class seats are checked for
     * @return A set of flights that leave at or after the departure time that have seats for the fare class
     */
    public Set<Flight> availableFlights (LocalTime departureTime, FareClass fareclass){
        Objects.requireNonNull(fareclass,"Airport availableFlights() - null FareClass");

        return outFlights.flightsAtOrAfter(departureTime).stream()
               .filter(fl -> fl.hasSeats(fareclass))
                .collect(Collectors.<Flight>toSet());
    }

    Set<Flight> availableFlightsFromRoute(RouteNode routeNode, FareClass fareclass){
        return availableFlights(routeNode.departureTime().getTime(), fareclass);
    }
}
