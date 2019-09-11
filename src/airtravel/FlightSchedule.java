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
            throw new NullPointerException("FlightSchedule departure time and arrival time parameters are null");
        } else if(departureTime == null) {
            throw new NullPointerException("FlightSchedule departure time is null");
        } else if (arrivalTime == null){
            throw new NullPointerException("FlightSchedule arrival time is null");
        } else if (departureTime.isBefore(arrivalTime)) { //All airplanes fly during the day so no LocalTime needs to be wrapped after midnight
            throw new IllegalArgumentException("Arrival time must ");
        } else {
            return new FlightSchedule(departureTime, arrivalTime);
        }
    }

    public final boolean isShort(Duration durationMax) {
        if(durationMax == null)
            throw new NullPointerException("FlightSchedule - isShort() method received null parameter");

        Duration travelTime = Duration.between(departureTime, arrivalTime);
        if( travelTime.compareTo(durationMax) <= 0  )
            return true;
        return false;
    }
}

