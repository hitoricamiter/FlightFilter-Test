package com.gridnine.testing;


import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filterImpl.*;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("===== Фильтр: вылеты, которые уже были (до текущего момента) =====");
        printFiltered(flights, new DepartureBeforeNowFilter());

        System.out.println("\n===== Фильтр: прилёт раньше вылета (некорректные полёты) =====");
        printFiltered(flights, new ArrivalBeforeDepartureFilter());

        System.out.println("\n===== Фильтр: слишком долгие пересадки (больше 2 часов) =====");
        printFiltered(flights, new ExcessiveGroundTimeFilter());
    }

    private static void printFiltered(List<Flight> flights, FlightFilter filter) {
        for (Flight flight : flights) {
            if (!(filter.test(flight))) {
                System.out.println(flight);
            }
        }
    }
}