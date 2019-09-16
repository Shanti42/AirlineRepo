package airtravel;

import java.util.Objects;

/**
 * Represents the fare class of a certain seat class
 */
public final class FareClass {

    //identifies the fare class
    private final int identifier;
    //the seat class the fare class applies to
    private final SeatClass seatClass;

    private FareClass(int identifier, SeatClass seatClass) {
        this.identifier = identifier;
        this.seatClass = seatClass;
    }

    public static final FareClass of(int identifier, SeatClass seatClass) {
        Objects.requireNonNull(identifier, "FareClass - of(), null identifier parameter provided");
        Objects.requireNonNull(seatClass, "FareClass - of(), null seat class parameter provided");
        return new FareClass(identifier, seatClass);
    }

    public int getIdentifier() {
        return identifier;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    @Override
    public int hashCode() {
        return identifier;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof FareClass) {
            FareClass fareClass = (FareClass) object;
            return this.getIdentifier() == fareClass.getIdentifier();
        }
        return false;
    }
}
