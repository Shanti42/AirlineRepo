package airtravel;

import java.time.LocalTime;
import java.time.Duration;

public abstract class AbstractFlight implements Flight {

    abstract public String getCode(); //Returns the flight identifier

    abstract public Leg getLeg(); //Returns the flight leg

    abstract public FlightSchedule getFlightSchedule(); //Returns a FlightSchedule

     public Airport origin() {
            return this.getLeg().getOrigin();
     }

    public Airport destination() {
         return this.getLeg().getDestination();
    }

    public LocalTime departureTime() {
         return this.getFlightSchedule().getDepartureTime();
    }

    public LocalTime arrivalTime(){
         return this.getFlightSchedule().getArrivalTime();
    }

    public boolean isShort(Duration durationMax){
         return this.getFlightSchedule().isShort(durationMax);
    }
}