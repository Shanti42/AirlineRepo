package airtravel;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Objects;

/**
 * Represents how many seats are available in a flight for each seat class
 */
public final class SeatConfiguration {

    //the representation of how many seats are available for each seat class
    private final EnumMap<SeatClass, Integer> seats;

    private SeatConfiguration(EnumMap<SeatClass, Integer> seats) {
        this.seats = seats.clone();
    }

    public static final SeatConfiguration of(EnumMap<SeatClass, Integer> seats) {
        Objects.requireNonNull(seats, "Null argument for seats given");
        return new SeatConfiguration(seats);
    }

    //Creates a copy of the given seat configuration
    public static final SeatConfiguration clone(SeatConfiguration seatConfiguration) {
        Objects.requireNonNull(seatConfiguration, "SeatConfiguration - clone() Received null Seat Configuration");
        return new SeatConfiguration(seatConfiguration.seats);
    }


    /**
     * Returns the number of seats available for the given seat class
     *
     * @param seatClass the seat class being checked for seats
     * @return the number of seats of the seat class,
     * or zero if the number is null or negative
     */
    public final int seats(SeatClass seatClass) {
        Objects.requireNonNull(seatClass, "SeatConfiguration - seats() Received null Seat Class parameter");
        Integer seatCount = seats.get(seatClass);
        if(seatCount == null || seatCount < 0) {
            return 0;
        } else {
            return seatCount;
        }
    }

    /**
     * Sets the number of seats available for the given seat class
     *
     * @param seatClass the seat class the value of seats are being set
     * @param seats     the number of seats available
     * @return The previous number of seats available
     */
    public final int setSeats(SeatClass seatClass, int seats) {
        Objects.requireNonNull(seatClass, "SeatConfiguration - setSeats() Received null seat class");
        try {
            return this.seats.put(seatClass, seats);
        } catch (Exception e){
            return 0;
        }
    }

    /**
     * Checks if there are seats in any class
     *
     * @return true if there are seats in any class, false otherwise
     */
    public final boolean hasSeats() {
        return seats.values().stream().anyMatch(val -> val > 0);
    }
}
