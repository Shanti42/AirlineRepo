package airtravel;

import java.time.LocalTime;
import java.util.Set;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.TreeMap;

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
        if (origin == null){
            throw new NullPointerException("FlightGroup - build() origin is null");
        } else {
            return new FlightGroup(origin);
        }
    }

    public Airport getOrigin() {
        return origin;
    }

    //Adds a flight to the collection mapped to its departure time
    public final boolean add(Flight flight){
        if(flight == null)
            throw new NullPointerException("FlightGroup - add() flight is null");
        else if(!origin.getCode().equals(flight.origin().getCode()))
            throw new IllegalArgumentException("Flights must originate from the same airport to be added");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(tempFlights == null)
            tempFlights = new HashSet<Flight>();

        if(!tempFlights.contains(flight)) {
            tempFlights.add(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

    //Removes a flight if it is mapped to the collection of flights at its departure time
    public final boolean remove(Flight flight){
        if(flight == null)
            throw new NullPointerException("FlightGroup - remove() flight is null");
        if(!origin.getCode().equals(flight.origin().getCode()))
            throw new IllegalArgumentException("Flights must originate from the same airport to be removed");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(tempFlights == null)
            return false;
        if(tempFlights.contains(flight)) {
            tempFlights.remove(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

    //Returns a set of all flights before or after the given departure time
    public final Set<Flight> flightsAtOrAfter(LocalTime departureTime) {
        if(departureTime == null)
            throw new NullPointerException("FlightGroup - flightsAtOrAfter() departureTime is null");

        Set<Flight> tempFlights = new HashSet<Flight>();
        Set<LocalTime> departTimes = flights.keySet();
        for(LocalTime departure: departTimes){
            if(departure.equals(departureTime) || departure.isBefore(departureTime))
                tempFlights.addAll(flights.get(departure));
        }
        return tempFlights;
    }

}
