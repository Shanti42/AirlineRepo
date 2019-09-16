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
        Objects.requireNonNull(code, "SimpleFlight - build() Received null code parameter");
        Objects.requireNonNull(leg, "SimpleFlight - build() Received null leg parameter");
        Objects.requireNonNull(flightSchedule, "SimpleFlight - build() Received null flight Schedule parameter");
        Objects.requireNonNull(seatsAvailable, "SimpleFlight - build() Received null seats available parameter");
        SimpleFlight newFlight = new SimpleFlight(code, leg, flightSchedule, seatsAvailable);
        leg.getOrigin().addFlight(newFlight);
        return newFlight;
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