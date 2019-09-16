package airtravel;

import java.util.Objects;

/**
 * A non-stop route from departure to a destination
 */
public final class Leg {

    // the departure airport of the journey
    private final Airport origin;
    // the destination airport of the journey
    private final Airport destination;

    private Leg(Airport origin, Airport destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public static final Leg of(Airport origin, Airport destination) {
        Objects.requireNonNull(origin, "Origin is null");
        Objects.requireNonNull(destination, "Destination is null");
        return new Leg(origin, destination);
    }
}
