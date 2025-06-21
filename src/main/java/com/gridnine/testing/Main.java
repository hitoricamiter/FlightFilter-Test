package com.gridnine.testing;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filterImpl.*;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.util.FlightBuilder;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

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
        int count = 1;
        for (Flight flight : flights) {
            if (!filter.test(flight)) {
                System.out.printf("Полёт №%d:%n", count++);
                printFlightDetails(flight);
                System.out.println();
            }
        }
        if (count == 1) {
            System.out.println("  Нет полётов, соответствующих фильтру.");
        }
    }

    private static void printFlightDetails(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments == null || segments.isEmpty()) {
            System.out.println("  Нет сегментов в полёте");
            return;
        }

        Duration totalFlightDuration = Duration.ZERO;

        for (int i = 0; i < segments.size(); i++) {
            Segment segment = segments.get(i);
            Duration segmentDuration = Duration.between(segment.getDepartureDate(), segment.getArrivalDate());
            totalFlightDuration = totalFlightDuration.plus(segmentDuration);

            System.out.printf("  Сегмент %d: Вылет %s, Прилёт %s (Длительность: %dч %dм)%n",
                    i + 1,
                    segment.getDepartureDate().format(formatter),
                    segment.getArrivalDate().format(formatter),
                    segmentDuration.toHours(),
                    segmentDuration.toMinutesPart());

            if (i < segments.size() - 1) {
                Duration groundTime = Duration.between(
                        segment.getArrivalDate(),
                        segments.get(i + 1).getDepartureDate()
                );
                System.out.printf("    Время на земле: %d ч %d мин%n", groundTime.toHours(), groundTime.toMinutesPart());
            }
        }

        System.out.printf("  Общее время в воздухе: %d ч %d мин%n", totalFlightDuration.toHours(), totalFlightDuration.toMinutesPart());
    }
}
