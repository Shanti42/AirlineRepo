package airtravel;

import java.time.LocalTime;

public final class SimpleFlight extends AbstractFlight {

    //Flight identifier
    private final String code;
    //Flight leg
    private final Leg leg;
    //Flight Schedule
    private final FlightSchedule flightSchedule;

    private SimpleFlight(String code, Leg leg, FlightSchedule flightSchedule) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
    }

    public static final SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule) {
        if (code == null || leg == null || flightSchedule == null){
            throw new NullPointerException("SimpleFlight - build() Received null parameters");
        }  else {
            leg.getOrigin().addFlight(new SimpleFlight(code,leg,flightSchedule));
            return new SimpleFlight(code, leg, flightSchedule);
        }
    }

    public String getCode(){
        return code;
    }

    public Leg getLeg(){
        return leg;
    }

    public FlightSchedule getFlightSchedule(){
        return flightSchedule;
    }

}