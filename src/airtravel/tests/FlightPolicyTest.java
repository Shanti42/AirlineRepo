package airtravel.tests;

import airtravel.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import static airtravel.SeatClass.*;
import java.time.Duration;
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
    void testFlightPolicySeatsAvailable() {

    }

    @Test
    void testFlightPolicyStrict() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight,blankPolicy);
        Flight strictFlight = FlightPolicy.strict(blankFlight);

        assertFalse(seatConfigSame(seatConfig,strictFlight.seatsAvailable(busnFareClass)));

        EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
        map.put(SeatClass.BUSINESS,10);
        SeatConfiguration onlyBis = SeatConfiguration.of(map);

        assertTrue(seatConfigSame(onlyBis,strictFlight.seatsAvailable(busnFareClass)));
    }

    @Test
    void testFlightPolicyRestrictedDuration() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight,blankPolicy);
        Duration shortDur = Duration.ofHours(3);
        Duration longDur = Duration.ofHours(12);

        Flight restricted = FlightPolicy.restrictedDuration(blankFlight, shortDur);
        assertTrue(seatConfigSame(seatConfig,restricted.seatsAvailable(busnFareClass)));

        restricted = FlightPolicy.restrictedDuration(blankFlight, longDur);
        assertFalse(seatConfigSame(seatConfig,restricted.seatsAvailable(econFareClass)));
    }

    @Test
    void testFlightPolicyReserve() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight,blankPolicy);

        Flight reservedOneSeat = FlightPolicy.reserve(blankFlight, 1);
        assertEquals(seatConfig.seats(PREMIUM_ECONOMY),reservedOneSeat.seatsAvailable(premFareClass).seats(PREMIUM_ECONOMY));

        assertFalse(seatConfigSame(seatConfig, reservedOneSeat.seatsAvailable(busnFareClass)));

    }

    @Test
    void testFlightPolicyLimited() {

    }

    protected BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy() {
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy = (seatConfig, fareClass) -> { return SeatConfiguration.clone(seatConfig); };
        return blankPolicy;
    }
}
