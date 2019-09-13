package airtravel;

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
        if (origin == null && destination == null) {
            throw new NullPointerException("Origin and destination are null");
        } else if (origin == null) {
            throw new NullPointerException("Origin is null");
        } else if (destination == null) {
            throw new NullPointerException("Destination is null");
        } else {
            return new Leg(origin, destination);
        }
    }
}
