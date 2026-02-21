package com.trafficcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trafficcounter.analytics.TrafficAnalytics;
import com.trafficcounter.domain.Record;
import com.trafficcounter.io.IsoRecordParser;
import com.trafficcounter.io.RecordsParser;
import java.util.List;
import org.junit.jupiter.api.Test;

class TrafficCounterTest {
    private static final String SAMPLE_INPUT = """
        2021-12-01T05:00:00 5
        2021-12-01T05:30:00 12
        2021-12-01T06:00:00 14
        2021-12-01T06:30:00 15
        2021-12-01T07:00:00 25
        2021-12-01T07:30:00 46
        2021-12-01T08:00:00 42
        2021-12-01T15:00:00 9
        2021-12-01T15:30:00 11
        2021-12-01T23:30:00 0
        2021-12-05T09:30:00 18
        2021-12-05T10:30:00 15
        2021-12-05T11:30:00 7
        2021-12-05T12:30:00 6
        2021-12-05T13:30:00 9
        2021-12-05T14:30:00 11
        2021-12-05T15:30:00 15
        """;

    @Test
    void totalCars() {
        List<Record> records = parseSample();
        TrafficAnalytics analytics = new TrafficAnalytics();
        assertEquals(260, analytics.totalCars(records));
    }

    @Test
    void dailyTotals() {
        List<Record> records = parseSample();
        TrafficAnalytics analytics = new TrafficAnalytics();
        assertEquals("{2021-12-01=179, 2021-12-05=81}", analytics.dailyTotals(records).toString());
    }

    @Test
    void topThreeHalfHours() {
        List<Record> records = parseSample();
        TrafficAnalytics analytics = new TrafficAnalytics();
        assertEquals(
            List.of("2021-12-01T07:30 46", "2021-12-01T08:00 42", "2021-12-01T07:00 25"),
            analytics.topHalfHours(records, 3).stream().map(Record::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriod() {
        List<Record> records = parseSample();
        TrafficAnalytics analytics = new TrafficAnalytics();
        assertEquals(
            List.of("2021-12-01T15:00 9", "2021-12-01T15:30 11", "2021-12-01T23:30 0"),
            analytics.leastTrafficPeriod(records, 3).stream().map(Record::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriodRequiresEnoughRecords() {
        List<Record> records = parseSample();
        TrafficAnalytics analytics = new TrafficAnalytics();
        assertThrows(
            IllegalArgumentException.class,
            () -> analytics.leastTrafficPeriod(records.subList(0, 2), 3)
        );
    }

    private List<Record> parseSample() {
        RecordsParser parser = new IsoRecordParser();
        return parser.parseLines(SAMPLE_INPUT.lines().toList());
    }
}
