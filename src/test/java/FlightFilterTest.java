import com.gridnine.testing.filterImpl.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filterImpl.DepartureBeforeNowFilter;
import com.gridnine.testing.filterImpl.ExcessiveGroundTimeFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightFilterTest {

    private static List<Flight> flights;

    @BeforeAll
    static void setup() {
        flights = FlightBuilder.createFlights();
    }

    @Test
    void arrivalBeforeDepartureFilter_validAndInvalidFlights() {
        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();

        assertTrue(filter.test(flights.get(0)));
        assertFalse(filter.test(flights.get(3)));
    }

    @Test
    void departureBeforeNowFilter_futureAndPastFlights() {
        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();

        assertTrue(filter.test(flights.get(0)));
        assertFalse(filter.test(flights.get(2)));
    }

    @Test
    void excessiveGroundTimeFilter_withVariousGroundTimes() {
        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();

        assertTrue(filter.test(flights.get(0)));
        assertFalse(filter.test(flights.get(4)));
        assertTrue(filter.test(flights.get(1)));
    }
}
