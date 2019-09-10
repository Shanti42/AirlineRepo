package airtravel;

import java.time.Duration;

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

    private Airport (String code, Duration connectionTimeMin){
        this.code = code;
        this.connectionTimeMin = connectionTimeMin;
    }

    public static final Airport of(String code, Duration connectionTimeMin){
        if (code == null && connectionTimeMin == null){
            throw new NullPointerException("Airport identifier code and connection time parameter are null");
        } else if(code == null) {
            throw new NullPointerException("Airport identifier code is null");
        } else if (connectionTimeMin == null){
            throw new NullPointerException("Connection time parameter is null");
        } else {
            return new Airport(code, connectionTimeMin);
        }
    }

    public String getCode() {
        return code;
    }

    public Duration getConnectionTimeMin(){
        return connectionTimeMin;
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if(object != null && object instanceof Airport) {
            Airport otheraAirport = (Airport) object;
            equals = getCode().equals(otheraAirport.code);
        }
        //else: equals is false, but value already assigned

        return equals;
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

    public final boolean addFlight(Flight flight){
        return outFlights.add(flight);
    }

    public final boolean removeFlight(Flight flight){
        return outFlights.remove(flight);
    }
}
