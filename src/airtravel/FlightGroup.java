package airtravel;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a set of flights that have the same origin airport
 * and that is organized by departure
 */
public final class FlightGroup {

    //Represents origin airport for all flights in group
    private final Airport origin;

    //Map of departure time to the collection of flights that have that departure time
    private final NavigableMap<LocalTime, Set<Flight>> flights = new TreeMap<LocalTime, Set<Flight>>();

    //flights is organized by departure time (based on Discussion board)
    private FlightGroup(Airport origin) {
        this.origin = origin;
    }

    public static final FlightGroup of(Airport origin) {
        Objects.requireNonNull(origin, "FlightGroup - build() origin is null");
        return new FlightGroup(origin);
    }

    //Adds a flight to the collection mapped to its departure time
    public final boolean add(Flight flight) {
        Objects.requireNonNull(flight, "FlightGroup - add() flight is null");
        if (!origin.getCode().equals(flight.origin().getCode())) {
            throw new IllegalArgumentException("Flights must originate from the same airport to be added");
        }
        return flights.computeIfAbsent(flight.departureTime(), flights -> new HashSet<>()).add(flight);
    }

    //Removes a flight if it is mapped to the collection of flights at its departure time
    public final boolean remove(Flight flight) {
        Objects.requireNonNull(flight,"FlightGroup - remove() flight is null");
        if (!origin.getCode().equals(flight.origin().getCode())) {
            throw new IllegalArgumentException("Flights must originate from the same airport to be removed");
        }
        return flights.computeIfPresent(flight.departureTime(), (key, oldVal) -> {
            oldVal.remove(flight);
            return oldVal;
        }) != null;
    }

    //Returns a set of all flights before or after the given departure time
    public final Set<Flight> flightsAtOrAfter(LocalTime departureTime) {
        Objects.requireNonNull(departureTime, "FlightGroup - flightsAtOrAfter() departureTime is null");
        return (Set) flights.tailMap(departureTime).values().stream().collect(Collectors.toSet());

    }

    public Airport getOrigin() {
        return origin;
    }

}
