package airtravel.tests;

import airtravel.*;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.function.BiFunction;

import static airtravel.SeatClass.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightPolicyTest extends FlightTest {
    FlightPolicy limitedFlight1;



    public FlightPolicyTest(){
        limitedFlight1 = FlightPolicy.limited(flight3));
    }

    @Test
    void testLimitedFlightPolicy(){
        SeatConfiguration buisRem = SeatConfiguration.clone(config3);
        buisRem.setSeats(BUSINESS, 0);
        SeatConfiguration econRem = SeatConfiguration.clone(config3);
        econRem.setSeats(ECONOMY, 0);
        SeatConfiguration econPremRem = SeatConfiguration.clone(config3);
        econPremRem.setSeats(ECONOMY, 0);
        econPremRem.setSeats(PREMIUM_ECONOMY, 0);

        assertTrue(seatConfigSame(buisRem, limitedFlight1.seatsAvailable(econFareClass)), "Test limited with lowest class passenger");
        assertTrue(seatConfigSame(econRem, limitedFlight1.seatsAvailable(premFareClass)), "Test limited with premium (middle) class passenger");
        assertTrue(seatConfigSame(econPremRem, limitedFlight1.seatsAvailable(busnFareClass)), "Test limited with highest class passenger");
    }

    @Test
    void testAdditionalPolicies(){
        /**
         * This policy takes into account the fare class of the passenger.
         * Passengers with a fare class identifier lower than the 5 have access to seats in the seat class above.
         * Passengers with a fare class identifier higher than 5 have access to their class.
         */
        FlightPolicy flightSeatPriority = FlightPolicy.of(flight3, (config3, econFareClass) -> {

            return null;
        });



    }



}
