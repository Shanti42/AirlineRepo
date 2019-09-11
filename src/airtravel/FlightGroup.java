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

    private final Airport origin;
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

    public final boolean add(Flight flight){
        if(flight == null)
            throw new NullPointerException("FlightGroup - add() flight is null");
        else if(!origin.getCode().equals(flight.getCode()))
            throw new IllegalArgumentException("Flights must originate from the same airport to be added");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(!tempFlights.contains(flight)) {
            tempFlights.add(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

    public final boolean remove(Flight flight){
        if(flight == null)
            throw new NullPointerException("FlightGroup - remove() flight is null");
        if(!origin.getCode().equals(flight.getCode()))
            throw new IllegalArgumentException("Flights must originate from the same airport to be removed");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(tempFlights.contains(flight)) {
            tempFlights.remove(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

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
