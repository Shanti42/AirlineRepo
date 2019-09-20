package airtravel;

import java.time.Duration;
import java.util.EnumMap;
import java.util.function.BiFunction;
import java.util.Objects;

import static airtravel.SeatClass.BUSINESS;

public final class FlightPolicy extends AbstractFlight {

    private final Flight flight;
    private final BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy;

    private FlightPolicy(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
        this.flight = flight;
        this.policy = policy;
    }

    public static final FlightPolicy of(Flight flight, BiFunction<SeatConfiguration, FareClass, SeatConfiguration> policy) {
        Objects.requireNonNull(flight, "FlightPolicy - build() received null flight parameter");
        Objects.requireNonNull(policy, "FlightPolicy - build() received null policy parameter");

        FlightPolicy newFlight = new FlightPolicy(flight, policy);
        flight.origin().removeFlight(flight);
        flight.origin().addFlight(newFlight);
        return newFlight;
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
        Objects.requireNonNull(fareClass, "FlightPolicy - seatsAvailable() Received null fare class parameter");

        SeatConfiguration seatConfig = flight.seatsAvailable(fareClass);
        return policy.apply(seatConfig, fareClass);
    }

    public static final Flight strict(Flight flight) {
        Objects.requireNonNull(flight, "FlightPolicy - strict() received null flight parameter");

        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> strictPolicy = (seatConfig, fareClass) -> {
            SeatClass seatClass = fareClass.getSeatClass();
            EnumMap<SeatClass, Integer> map = new EnumMap<>(SeatClass.class);
            map.put(seatClass, seatConfig.seats(seatClass));
            return SeatConfiguration.of(map);
        };

        return FlightPolicy.of(flight, strictPolicy);
    }

    public static final Flight restrictedDuration(Flight flight, Duration durationMax) {
        Objects.requireNonNull(flight, "FlightPolicy - restrictedDuration() received null flight parameter");

        if(flight.isShort(durationMax)) {
            return strict(flight);
        } else {
            BiFunction<SeatConfiguration, FareClass, SeatConfiguration> restrictedPolicy = (seatConfig, fareClass) -> { return SeatConfiguration.clone(seatConfig); };
            return FlightPolicy.of(flight, restrictedPolicy);
        }
    }

    public static final Flight reserve(Flight flight, int reserve) {
        Objects.requireNonNull(flight, "FlightPolicy - reserve() received null flight parameter");

        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> reservePolicy = (seatConfig, fareClass) -> {
          EnumMap<SeatClass, Integer> map = new EnumMap<>(SeatClass.class);
            for (SeatClass seatClass : SeatClass.values()) {
                map.put(seatClass, Math.max(0, seatConfig.seats(seatClass) - reserve));
            }
          return SeatConfiguration.of(map);
        };

        return FlightPolicy.of(flight,reservePolicy);
    }

    public static final Flight limited(Flight flight) {
        Objects.requireNonNull(flight, "FlightPolicy - limited() received null flight parameter");

        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> limitedPolicy = (seatConfig, fareClass) -> {
            SeatClass seatClass = fareClass.getSeatClass();
            EnumMap<SeatClass, Integer> map = new EnumMap<>(SeatClass.class);
            map.put(seatClass, seatConfig.seats(seatClass));
            if(seatClass.ordinal() < SeatClass.values().length-1) {
                SeatClass classAbove = SeatClass.values()[seatClass.ordinal()+1];
                map.put(classAbove, seatConfig.seats(classAbove));
            }
            return SeatConfiguration.of(map);
        };

        return FlightPolicy.of(flight,limitedPolicy);

    }

}
