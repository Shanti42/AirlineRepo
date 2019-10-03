package airtravel.tests;

import airtravel.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static airtravel.SeatClass.*;

import java.time.Duration;
import java.util.EnumMap;
import java.util.function.BiFunction;

/**
 * FlightPolicy class Tests
 */
public class FlightPolicyTest extends FlightTest {

    public BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy;

    /**
     * FlightPolicy build method test
     */
    @Test
    void testFlightPolicyOf() {
        blankPolicy = blankPolicy();
        assertNotNull(FlightPolicy.of(flight, blankPolicy));
        assertThrows(NullPointerException.class, () -> FlightPolicy.of(null, blankPolicy));
        assertThrows(NullPointerException.class, () -> FlightPolicy.of(flight, null));

    }

    /**
     * FlightPolicy seatsAvailable() method test
     */
    @Test
    void testFlightPolicySeatsAvailable() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight, blankPolicy);
        assertTrue(seatConfigSame(seatConfig, blankFlight.seatsAvailable(busnFareClass)));
        assertFalse(seatConfigSame(config1, blankFlight.seatsAvailable(busnFareClass)));

    }

    /**
     * FlightPolicy strict() method test
     */
    @Test
    void testFlightPolicyStrict() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight, blankPolicy);
        Flight strictFlight = FlightPolicy.strict(blankFlight);

        assertFalse(seatConfigSame(seatConfig, strictFlight.seatsAvailable(busnFareClass)));

        EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
        map.put(SeatClass.BUSINESS, 10);
        SeatConfiguration onlyBis = SeatConfiguration.of(map);

        assertTrue(seatConfigSame(onlyBis, strictFlight.seatsAvailable(busnFareClass)));
    }

    /**
     * FlightPolicy limited() method test
     */
    @Test
    void testFlightPolicyLimited() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight, blankPolicy);
        Flight limitedFlight1 = FlightPolicy.limited(blankFlight);
        SeatConfiguration buisRem = SeatConfiguration.clone(seatConfig);
        buisRem.setSeats(BUSINESS, 0);
        SeatConfiguration econRem = SeatConfiguration.clone(seatConfig);
        econRem.setSeats(ECONOMY, 0);
        EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
        map.put(SeatClass.BUSINESS, 10);
        SeatConfiguration econPremRem  = SeatConfiguration.of(map);

        
        assertTrue(seatConfigSame(econPremRem, limitedFlight1.seatsAvailable(busnFareClass)), "Test limited with highest class passenger");
    }

    /**
     * FlightPolicy restrictedDuration() method test
     */
    @Test
    void testFlightPolicyRestrictedDuration() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight, blankPolicy);
        Duration shortDur = Duration.ofHours(3);
        Duration longDur = Duration.ofHours(12);

        Flight restricted = FlightPolicy.restrictedDuration(blankFlight, shortDur);
        assertTrue(seatConfigSame(seatConfig, restricted.seatsAvailable(busnFareClass)));

        restricted = FlightPolicy.restrictedDuration(blankFlight, longDur);
        assertFalse(seatConfigSame(seatConfig, restricted.seatsAvailable(econFareClass)));
    }

    /**
     * FlightPolicy reserve() method test
     */
    @Test
    void testFlightPolicyReserve() {
        blankPolicy = blankPolicy();
        FlightPolicy blankFlight = FlightPolicy.of(flight, blankPolicy);

        Flight reservedOneSeat = FlightPolicy.reserve(blankFlight, 1);
        assertEquals(seatConfig.seats(PREMIUM_ECONOMY), reservedOneSeat.seatsAvailable(premFareClass).seats(PREMIUM_ECONOMY));

        assertFalse(seatConfigSame(seatConfig, reservedOneSeat.seatsAvailable(busnFareClass)));

    }

    /**
     * FlightPolicy - Test two newly created policies (as anonymous classes)
     */
    @Test
    void testAdditionalPolicies() {
        /**
         * This policy takes into account the fare class of the passenger.
         * Passengers with a fare class identifier lower than or equal to 5 have access to seats in the seat class above and their class.
         * Passengers with a fare class identifier higher than 5 have access to their class.
         */
        blankPolicy = blankPolicy();
        Flight blankFlight = FlightPolicy.of(flight, blankPolicy);

        Flight testSelectiveRest = FlightPolicy.of(flight, (config, fareClass) -> {
            SeatClass seatClass = fareClass.getSeatClass();
            EnumMap<SeatClass, Integer> map = justYourClass(seatClass, config);

            if (fareClass.getIdentifier() > 5 && seatClass.ordinal() < SeatClass.values().length - 1) {
                SeatClass classAbove = SeatClass.values()[seatClass.ordinal() + 1];
                map.put(classAbove, config.seats(classAbove));
            }
            return SeatConfiguration.of(map);

        });

        EnumMap<SeatClass, Integer> selectiveMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        selectiveMap.put(ECONOMY, 20);
        selectiveMap.put(BUSINESS, 0);
        selectiveMap.put(PREMIUM_ECONOMY, 0);

        assertTrue(seatConfigSame(SeatConfiguration.of(selectiveMap), testSelectiveRest.seatsAvailable(econFareClass)), "Test selective Economy");

        selectiveMap.put(ECONOMY, 0);
        selectiveMap.put(BUSINESS, 10);
        selectiveMap.put(PREMIUM_ECONOMY, 0);

        assertTrue(seatConfigSame(SeatConfiguration.of(selectiveMap), testSelectiveRest.seatsAvailable(busnFareClass)), "Test selective Business");
        assertTrue(seatConfigSame(SeatConfiguration.of(selectiveMap), testSelectiveRest.seatsAvailable(busnFareClassLow)), "Test indexing correct");
        /**
         * This policy never allows anyone to fly Economy
         *
         */
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> noEconomy = (config, fareClass) -> {
            EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
            map.put(ECONOMY, 0);
            return SeatConfiguration.of(map);
        };
        EnumMap<SeatClass, Integer> noEconMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
        noEconMap.put(ECONOMY, 0);

        assertTrue(seatConfigSame(SeatConfiguration.of(noEconMap), FlightPolicy.of(flight, noEconomy).seatsAvailable(econFareClass)));
    }


    EnumMap<SeatClass, Integer> justYourClass(SeatClass seatClass, SeatConfiguration seatConfiguration) {
        EnumMap<SeatClass, Integer> map = new EnumMap<>(SeatClass.class);
        map.put(seatClass, seatConfiguration.seats(seatClass));
        return map;
    }


    /**
     * FlightPolicy - test the composition of multiple policies
     */
    @Test
    void testPolicyComposition() {
        blankPolicy = blankPolicy();

        FlightPolicy blankFlight = FlightPolicy.of(flight,blankPolicy);

        Flight reservedStrictFlight = FlightPolicy.reserve(FlightPolicy.strict(blankFlight), 1);
        EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);
        map.put(SeatClass.BUSINESS,9);

        assertTrue(seatConfigSame(SeatConfiguration.of(map), reservedStrictFlight.seatsAvailable(busnFareClass)));

    }

    /**
     * Helper Routine - Creates a policy with no restrictions
     */
    protected BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy() {
        BiFunction<SeatConfiguration, FareClass, SeatConfiguration> blankPolicy = (seatConfig, fareClass) -> {
            return SeatConfiguration.clone(seatConfig);
        };
        return blankPolicy;
    }
}
