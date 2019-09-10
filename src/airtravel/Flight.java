package airtravel;

import java.time.LocalTime;
import java.time.Duration;

/**
 * Represents flights of different types.
 * A flight represents the trip of an airplane that spans a leg following a timetable
 */
public interface Flight {

    public String getCode(); //Returns the flight identifier

    public Leg getLeg(); //Returns the flight leg

    public Airport origin(); //Return start point of leg

    public Airport destination(); //Returns the end point of leg

    public FlightSchedule getFlightSchedule(); //Returns a FlightSchedule

    public LocalTime departureTime(); // Returns departureTime

    public LocalTime arrivalTime(); //Returns arrivalTime

    public boolean isShort(Duration durationMax); //Returns the value of isShort method
}