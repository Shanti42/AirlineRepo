package airtravel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A class that keeps track of departure and arrival time of a single flight
 */
public final class FlightSchedule {

    //Time the flight departs from the origin airport
    private final LocalTime departureTime;

    //Time the flight arrives at the destination airport
    private final LocalTime arrivalTime;

    private FlightSchedule(LocalTime departureTime, LocalTime arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    //build method
    public static final FlightSchedule of(LocalTime departureTime, LocalTime arrivalTime) {
        Objects.requireNonNull(departureTime, "FlightSchedule - build() departure time is null");
        Objects.requireNonNull(arrivalTime, "FlightSchedule - build() arrivalTime is null");
        if (!departureTime.isBefore(arrivalTime)) { //All airplanes fly during the day so no LocalTime needs to be wrapped after midnight
            throw new IllegalArgumentException("Departure time must be earlier than arrival time");
        } else {
            return new FlightSchedule(departureTime, arrivalTime);
        }
    }

    //Returns whether the flight is shorter then the given duration or not
    public final boolean isShort(Duration durationMax) {
        Objects.requireNonNull(durationMax, "FlightSchedule - isShort() durationMax is null");
        Duration travelTime = Duration.between(departureTime, arrivalTime);
        return travelTime.compareTo(durationMax) <= 0;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
}

