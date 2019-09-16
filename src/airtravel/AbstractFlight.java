package airtravel;

import java.time.LocalTime;
import java.time.Duration;
import java.util.Objects;

public abstract class AbstractFlight implements Flight {

    abstract public String getCode(); //Returns the flight identifier

    abstract public Leg getLeg(); //Returns the flight leg

    abstract public FlightSchedule getFlightSchedule(); //Returns a FlightSchedule

    abstract public SeatConfiguration seatsAvailable(FareClass fareClass);

    public Airport origin() {
        return this.getLeg().getOrigin();
    }

    public Airport destination() {
        return this.getLeg().getDestination();
    }

    public LocalTime departureTime() {
        return this.getFlightSchedule().getDepartureTime();
    }

    public LocalTime arrivalTime() {
        return this.getFlightSchedule().getArrivalTime();
    }

    //Returns whether the flight is shorter then the given duration
    public boolean isShort(Duration durationMax) {
        Objects.requireNonNull(durationMax, "Flight - isShort() durationMax is null");
        return this.getFlightSchedule().isShort(durationMax);
    }

    //returns whether the flight has any seats available for the given fare class
    @Override
    public boolean hasSeats(FareClass fareClass) {
        Objects.requireNonNull(fareClass, "hasSeats() - null fare class parameter provided");
        return seatsAvailable(fareClass).hasSeats();
    }
}