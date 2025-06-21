package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

@FunctionalInterface
public interface FlightFilter {
    boolean test(Flight flight);
}