package airtravel;

import java.time.LocalTime;
import java.util.Objects;

public final class SimpleFlight extends AbstractFlight {

    //Flight identifier
    private final String code;
    //Flight leg
    private final Leg leg;
    //Flight Schedule
    private final FlightSchedule flightSchedule;
    //Seats available in each seat class on the flight
    private final SeatConfiguration seatsAvailable;

    private SimpleFlight(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
        this.seatsAvailable = SeatConfiguration.clone(seatsAvailable);
    }

    public static final SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        Objects.requireNonNull(code, "SimpleFlight - of() Received null code parameter");
        Objects.requireNonNull(leg, "SimpleFlight - of() Received null leg parameter");
        Objects.requireNonNull(flightSchedule, "SimpleFlight - of() Received null flight Schedule parameter");
        Objects.requireNonNull(seatsAvailable, "SimpleFlight - of() Received null seats available parameter");
        SimpleFlight newFlight = new SimpleFlight(code, leg, flightSchedule, seatsAvailable);
        leg.getOrigin().addFlight(newFlight);
        return newFlight;
    }

    //Returns a copy of all seats available, regardless of fare class
    @Override
    public SeatConfiguration seatsAvailable(FareClass fareClass) {
        Objects.requireNonNull(fareClass, "SimpleFlight - seatsAvailable() Received null fare class parameter");
        return SeatConfiguration.clone(seatsAvailable);
    }

    public String getCode() {
        return code;
    }

    public Leg getLeg() {
        return leg;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

}