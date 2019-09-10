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
            throw new NullPointerException("FlightGroup origin Airport is null");
        } else {
            return new FlightGroup(origin);
        }
    }

    public Airport getOrigin() {
        return origin;
    }

    public final boolean add(Flight flight){
        if(!origin.getCode().equals(flight.getCode()))
            throw new IllegalArgumentException("Flight must originate must the same airport to be add");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(!tempFlights.contains(flight)) {
            tempFlights.add(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

    public final boolean remove(Flight flight){
        if(!origin.getCode().equals(flight.getCode()))
            throw new IllegalArgumentException("Flight must originate must the same airport to be add");

        Set<Flight> tempFlights = flights.get(flight.departureTime());
        if(tempFlights.contains(flight)) {
            tempFlights.remove(flight);
            flights.put(flight.departureTime(), tempFlights);
            return true;
        }
        return false;
    }

    public final Set<Flight> flightsAtOrAfter(LocalTime departureTime) {
        Set<Flight> tempFlights = new HashSet<Flight>();
        Set<LocalTime> departTimes = flights.keySet();
        for(LocalTime departure: departTimes){
            if(departure.equals(departureTime) || departure.isBefore(departureTime))
                tempFlights.addAll(flights.get(departure));
        }
        return tempFlights;
    }

}
