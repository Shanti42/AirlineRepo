package airtravel.tests;

import airtravel.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.function.BiFunction;

public class FlightPolicyTest extends FlightTest {

    public BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy;
    @Test
    void testFlightPolicyOf() {
        blankPolicy = blankPolicy();
        assertNotNull(FlightPolicy.of(flight,blankPolicy));
        assertThrows(NullPointerException.class, () -> FlightPolicy.of(null,blankPolicy));
        assertThrows(NullPointerException.class, () -> FlightPolicy.of(flight,null));

    }

    @Test
    void testFlightPolicyStatic() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight,blankPolicy);
        Flight strictFlight = FlightPolicy.strict(blankFlight);

        assertFalse(seatConfigSame(seatConfig,strictFlight.seatsAvailable(busnFareClass)));

        EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
        map.put(SeatClass.BUSINESS,10);
        SeatConfiguration onlyBis = SeatConfiguration.of(map);

        assertTrue(seatConfigSame(onlyBis,strictFlight.seatsAvailable(busnFareClass)));
    }

    protected BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy() {
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy = (seatConfig, fareClass) -> { return SeatConfiguration.clone(seatConfig); };
        return blankPolicy;
    }
}
