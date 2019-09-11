package airtravel;

import java.time.Duration;
import java.time.LocalTime;

/**
 * A class that keeps track of departure and arrival time of a single flight
 */
public final class FlightSchedule {

    private final LocalTime departureTime;
    private final LocalTime arrivalTime;

    private FlightSchedule(LocalTime departureTime, LocalTime arrivalTime)  {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public static final FlightSchedule of(LocalTime departureTime, LocalTime arrivalTime) {
        if (departureTime == null && arrivalTime == null){
            throw new NullPointerException("FlightSchedule - build() both departureTime and arrivalTime are null");
        } else if(departureTime == null) {
            throw new NullPointerException("FlightSchedule - build() departure time is null");
        } else if (arrivalTime == null){
            throw new NullPointerException("FlightSchedule - build() arrivalTime is null");
        } else if (departureTime.isBefore(arrivalTime)) { //All airplanes fly during the day so no LocalTime needs to be wrapped after midnight
            throw new IllegalArgumentException("Departure time must be earlier than arrival time");
        } else {
            return new FlightSchedule(departureTime, arrivalTime);
        }
    }

    public final boolean isShort(Duration durationMax) {
        if(durationMax == null)
            throw new NullPointerException("FlightSchedule - isShort() durationMax is null");

        Duration travelTime = Duration.between(departureTime, arrivalTime);
        return travelTime.compareTo(durationMax) <= 0;
    }
}

