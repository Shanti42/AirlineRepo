package airtravel;

import java.util.Objects;
import java.util.Set;

public final class RouteNode implements Comparable<RouteNode> {

    private final Airport airport;
    private final RouteTime arrivalTime;

    //Null previous denotes that this node is the original departure airport
    private final RouteNode previous;

    public Airport getAirport() { return airport; }

    public RouteTime getArrivalTime() { return arrivalTime; }

    public final RouteNode getPrevious() { return previous; }

    private RouteNode(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        this.airport = airport;
        this.arrivalTime = arrivalTime;
        this.previous = previous;
    }

    public static final RouteNode of(Airport airport, RouteTime arrivalTime, RouteNode previous) {
        Objects.requireNonNull(airport, "Received null airport");
        Objects.requireNonNull(arrivalTime, "Received null arrivalTime");

        return new RouteNode(airport, arrivalTime, previous);
    }

    public static final RouteNode of(Flight flight, RouteNode previous) {
        Objects.requireNonNull(flight, "flight received null");

        return new RouteNode(flight.destination(), new RouteTime(flight.arrivalTime()), previous);
    }

    public static final RouteNode of(Airport airport) {
        Objects.requireNonNull(airport,"received null airport");

        return new RouteNode(airport, RouteTime.UNKNOWN,null);

    }

    public final Boolean isArrivalTimeKnown() {
        return getArrivalTime().isKnown();
    }

    public final RouteTime departureTime() {
        return getArrivalTime().plus(getAirport().getConnectionTimeMin());
    }

    public Set<Flight> availableFlights(FareClass fareClass) {
        return airport.availableFlights(this.departureTime().getTime(), fareClass);
    }

    @Override
    public int compareTo(RouteNode other) {
        Objects.requireNonNull(other, "RouteNode, compareTo() -> Null parameter for other RouteNode");
        if( this.getArrivalTime().compareTo(other.getArrivalTime()) == 0 ) {
            return this.getAirport().compareTo(other.getAirport());
        }
        else {
            return this.getArrivalTime().compareTo(other.getArrivalTime());
        }

    }

}
