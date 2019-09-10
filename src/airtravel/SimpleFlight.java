package airtravel;

import java.time.LocalTime;

public final class SimpleFlight extends AbstractFlight {

    private final String code;
    private final Leg leg;
    private final FlightSchedule flightSchedule;

    private SimpleFlight(String code, Leg leg, FlightSchedule flightSchedule) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
    }

    public static final SimpleFlight of(String code, Leg leg, FlightSchedule flightSchedule) {
        if (code == null || leg == null || flightSchedule == null){
            throw new NullPointerException("SimpleFlight build method called with one or more null parameters");
        }  else {

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