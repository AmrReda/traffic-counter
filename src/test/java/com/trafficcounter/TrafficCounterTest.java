package com.trafficcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trafficcounter.app.TrafficCounterApplication;
import com.trafficcounter.app.TrafficCounterApplication.Record;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class TrafficCounterTest {
    @Test
    void totalCars() {
        List<Record> records = sampleRecords();
        assertEquals(260, TrafficCounterApplication.totalCars(records));
    }

    @Test
    void dailyTotals() {
        List<Record> records = sampleRecords();
        assertEquals("{2021-12-01=179, 2021-12-05=81}", TrafficCounterApplication.dailyTotals(records).toString());
    }

    @Test
    void topThreeHalfHours() {
        List<Record> records = sampleRecords();
        assertEquals(
            List.of("2021-12-01T07:30 46", "2021-12-01T08:00 42", "2021-12-01T07:00 25"),
            TrafficCounterApplication.topHalfHours(records, 3).stream().map(Record::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriod() {
        List<Record> records = sampleRecords();
        assertEquals(
            List.of("2021-12-01T15:00 9", "2021-12-01T15:30 11", "2021-12-01T23:30 0"),
            TrafficCounterApplication.leastTrafficPeriod(records, 3).stream().map(Record::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriodRequiresEnoughRecords() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TrafficCounterApplication.leastTrafficPeriod(sampleRecords().subList(0, 2), 3)
        );
    }

    private List<Record> sampleRecords() {
        return List.of(
            new Record(LocalDateTime.parse("2021-12-01T05:00:00"), 5),
            new Record(LocalDateTime.parse("2021-12-01T05:30:00"), 12),
            new Record(LocalDateTime.parse("2021-12-01T06:00:00"), 14),
            new Record(LocalDateTime.parse("2021-12-01T06:30:00"), 15),
            new Record(LocalDateTime.parse("2021-12-01T07:00:00"), 25),
            new Record(LocalDateTime.parse("2021-12-01T07:30:00"), 46),
            new Record(LocalDateTime.parse("2021-12-01T08:00:00"), 42),
            new Record(LocalDateTime.parse("2021-12-01T15:00:00"), 9),
            new Record(LocalDateTime.parse("2021-12-01T15:30:00"), 11),
            new Record(LocalDateTime.parse("2021-12-01T23:30:00"), 0),
            new Record(LocalDateTime.parse("2021-12-05T09:30:00"), 18),
            new Record(LocalDateTime.parse("2021-12-05T10:30:00"), 15),
            new Record(LocalDateTime.parse("2021-12-05T11:30:00"), 7),
            new Record(LocalDateTime.parse("2021-12-05T12:30:00"), 6),
            new Record(LocalDateTime.parse("2021-12-05T13:30:00"), 9),
            new Record(LocalDateTime.parse("2021-12-05T14:30:00"), 11),
            new Record(LocalDateTime.parse("2021-12-05T15:30:00"), 15)
        );
    }
}
