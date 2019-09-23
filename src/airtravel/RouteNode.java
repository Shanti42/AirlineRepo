package airtravel;

import java.util.Objects;

public final class RouteNode implements Comparable<RouteNode> {

    private final Airport airport;
    private final RouteTime arrivalTime;

    //Null previous denotes that this node is the original departure airport
    private final RouteNode previous;

    public Airport getAirport() { return airport; }

    public RouteTime getArrivalTime() { return arrivalTime; }

    public final RouteNode getPrevious() { return previous; }'

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
        Objects.requireNonNull(flight);

        return new RouteNode(flight.destination(), plus(flight.arrivalTime()), previous);
    }

    public static final RouteNode of(Airport airport) {

    }

    /**
     *
     */
}
