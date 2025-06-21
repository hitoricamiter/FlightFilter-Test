package com.gridnine.testing.filterImpl;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ExcessiveGroundTimeFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_MINUTES = 120;

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() <= 1) return true;

        long totalGroundTime = 0;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrivalTime = segments.get(i).getArrivalDate();
            LocalDateTime nextDepartureTime = segments.get(i + 1).getDepartureDate();

            long groundTime = Duration.between(arrivalTime, nextDepartureTime).toMinutes();
            totalGroundTime += groundTime;
        }

        return totalGroundTime <= MAX_GROUND_TIME_MINUTES;
    }
}

