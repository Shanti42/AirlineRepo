package airtravel;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.Objects;

public final class FlightPolicy extends AbstractFlight {

    private final Flight flight;
    private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;

    private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
        this.flight = flight;
        this.policy = policy;
    }

    public FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
        Objects.requireNonNull(flight, "FlightPolicy - build() received null flight parameter");
        Objects.requireNonNull(policy, "FlightPolicy - build() received null policy parameter");


    }
    public String getCode() {
        return flight.getCode();
    }

    public Leg getLeg() {
        return flight.getLeg();
    }

    public FlightSchedule getFlightSchedule() {
        return flight.getFlightSchedule();
    }

    public SeatConfiguration seatsAvailable(FareClass fareClass) {
        Objects.requireNonNull(fareClass, "SimpleFlight - seatsAvailable() Received null fare class parameter");

        SeatConfiguration seatConfig = flight.seatsAvailable(fareClass);
        return policy.apply(seatConfig, fareClass);
    }

    public static Flight strict(Flight flight) {

    }

    public static Flight restrictedDuration(Flight flight, Duration durationMax) {

    }

    public static Flight reserve(Flight flight, int reserve) {

    }

    public static Flight limited(Flight flight) {

    }

}
