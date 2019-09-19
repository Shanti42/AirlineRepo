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
    private final NavigableMap<LocalTime, Set<Flight>> flights =  new TreeMap<LocalTime, Set<Flight>>();

    //flights is organized by departure time (based on Discussion board)
    private FlightGroup(Airport origin) {
        this.origin = origin;
    }

    public static final FlightGroup of(Airport origin) {
        Objects.requireNonNull(origin, "FlightGroup - build() origin is null");
        return new FlightGroup(origin);
    }

    //Adds a flight to the collection mapped to its departure time
    public final boolean add(Flight flight){
        Objects.requireNonNull(flight, "FlightGroup - add() flight is null");
        if(!origin.getCode().equals(flight.origin().getCode())) {
            throw new IllegalArgumentException("Flights must originate from the same airport to be added");
        }
        return flights.computeIfAbsent(flight.departureTime(), flights ->new HashSet<>()).add(flight);
        /*
        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(tempFlights == null) {
            tempFlights = new HashSet<Flight>();
        }
        if(!tempFlights.contains(flight)) {
            tempFlights.add(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;*/
    }

    //Removes a flight if it is mapped to the collection of flights at its departure time
    public final boolean remove(Flight flight){
        Objects.requireNonNull("FlightGroup - remove() flight is null");
        if(!origin.getCode().equals(flight.origin().getCode())) {
            throw new IllegalArgumentException("Flights must originate from the same airport to be removed");
        }
        return flights.computeIfPresent(flight.departureTime(), (key, oldVal) -> { oldVal.remove(flight);
            return oldVal;}) != null;
    }

    //Returns a set of all flights before or after the given departure time
    public final Set<Flight> flightsAtOrAfter(LocalTime departureTime) {
        Objects.requireNonNull(departureTime, "FlightGroup - flightsAtOrAfter() departureTime is null");
        Set set = flights.tailMap(departureTime).values().stream().collect(Collectors.toSet());
        return set;
       /*
        Set<Flight> tempFlights = new HashSet<Flight>();
        Set<LocalTime> departTimes = flights.keySet();
        for(LocalTime iterTime: departTimes){
            if(iterTime.equals(departureTime) || iterTime.isBefore(departureTime)) {
                tempFlights.addAll(flights.get(iterTime));
            }
        }
        return tempFlights;*/
    }

    public Airport getOrigin() {
        return origin;
    }

}
