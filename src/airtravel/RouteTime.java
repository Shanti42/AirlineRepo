package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents the arrival time of a passenger at an airport
 */
public final class RouteTime implements Comparable<RouteTime> {


    private final LocalTime routeTime;

    public static final RouteTime UNKNOWN = new RouteTime(null);

    public RouteTime(LocalTime routeTime) {
        //allow null routeTime
        this.routeTime = routeTime;
    }

    //returns true if route time is known, false otherwise
    public boolean isKnown() {
        return routeTime != null;
    }

    public LocalTime getTime() {
        if (routeTime == null) {
            throw new IllegalStateException("Route time is unknown");
        } else {
            return routeTime;
        }
    }

    public RouteTime plus(Duration duration) {
        Objects.requireNonNull(duration, "RouteTime, plus() -> Null duration parameter");

        return isKnown() ? new RouteTime((LocalTime) duration.addTo(routeTime)) : UNKNOWN;
    }


    static RouteTime of(LocalTime localTime){
        //allow null localTime
        return new RouteTime(localTime);
    }


    @Override
    public int compareTo(RouteTime other) {
        Objects.requireNonNull(other, "RouteTime, compareTo() -> Null parameter for other RouteTime");
        if (!this.isKnown() || !other.isKnown()) {
            return handleUnknownRouteTimes(this, other);
        } else {
            return routeTime.compareTo(other.getTime());
        }
    }


    private int handleUnknownRouteTimes(RouteTime routeTime1, RouteTime routeTime2) {
        if (!routeTime1.isKnown() && !routeTime2.isKnown()) {
            return 0;
        } else {
            return routeTime1.isKnown() ? 1 : -1;
        }
    }

}
