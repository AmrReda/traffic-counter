package com.trafficcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.trafficcounter.analytics.TrafficAnalytics;
import com.trafficcounter.domain.TrafficRecord;
import com.trafficcounter.io.IsoTrafficRecordParser;
import com.trafficcounter.io.TrafficRecordsParser;
import java.util.List;
import org.junit.jupiter.api.Test;

class TrafficAnalyticsTest {
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

    private static final TrafficRecordsParser PARSER = new IsoTrafficRecordParser();
    private static final List<TrafficRecord> SAMPLE_RECORDS = PARSER.parseLines(SAMPLE_INPUT.lines().toList());
    private static final List<String> EXPECTED_TOP_THREE = List.of(
        "2021-12-01T07:30 46",
        "2021-12-01T08:00 42",
        "2021-12-01T07:00 25"
    );
    private static final List<String> EXPECTED_LEAST_PERIOD = List.of(
        "2021-12-01T15:00 9",
        "2021-12-01T15:30 11",
        "2021-12-01T23:30 0"
    );

    private final TrafficAnalytics analytics = new TrafficAnalytics();

    @Test
    void handlesEmptyInput() {
        List<TrafficRecord> records = parseLines("");
        assertEquals(0, analytics.totalCars(records));
        assertEquals("{}", analytics.dailyTotals(records).toString());
        assertEquals(List.of(), analytics.topHalfHours(records, 3));
        assertThrows(IllegalArgumentException.class, () -> analytics.leastTrafficPeriod(records, 3));
    }

    @Test
    void handlesSingleDayOnly() {
        List<TrafficRecord> records = parseLines(
            """
            2021-12-01T05:00:00 5
            2021-12-01T05:30:00 12
            2021-12-01T06:00:00 14
            """
        );

        assertEquals(31, analytics.totalCars(records));
        assertEquals("{2021-12-01=31}", analytics.dailyTotals(records).toString());
    }

    @Test
    void totalCars() {
        assertEquals(260, analytics.totalCars(SAMPLE_RECORDS));
    }

    @Test
    void dailyTotals() {
        assertEquals("{2021-12-01=179, 2021-12-05=81}", analytics.dailyTotals(SAMPLE_RECORDS).toString());
    }

    @Test
    void topThreeHalfHours() {
        assertEquals(EXPECTED_TOP_THREE, analytics.topHalfHours(SAMPLE_RECORDS, 3).stream().map(TrafficRecord::asLine).toList());
    }

    @Test
    void topThreeHalfHoursTieBreaksByEarlierTimestamp() {
        List<TrafficRecord> records = parseLines(
            """
            2021-12-01T01:00:00 10
            2021-12-01T00:00:00 10
            2021-12-01T00:30:00 10
            2021-12-01T02:00:00 9
            """
        );

        assertEquals(
            List.of("2021-12-01T00:00 10", "2021-12-01T00:30 10", "2021-12-01T01:00 10"),
            analytics.topHalfHours(records, 3).stream().map(TrafficRecord::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriod() {
        assertEquals(
            EXPECTED_LEAST_PERIOD,
            analytics.leastTrafficPeriod(SAMPLE_RECORDS, 3).stream().map(TrafficRecord::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriodChoosesFirstWindowOnTie() {
        List<TrafficRecord> records = parseLines(
            """
            2021-12-01T00:00:00 1
            2021-12-01T00:30:00 1
            2021-12-01T01:00:00 1
            2021-12-01T01:30:00 1
            """
        );

        assertEquals(
            List.of("2021-12-01T00:00 1", "2021-12-01T00:30 1", "2021-12-01T01:00 1"),
            analytics.leastTrafficPeriod(records, 3).stream().map(TrafficRecord::asLine).toList()
        );
    }

    @Test
    void leastTrafficUsesLineOrderEvenWhenTimestampsAreNonContiguous() {
        List<TrafficRecord> records = parseLines(
            """
            2021-12-01T00:00:00 50
            2021-12-02T23:30:00 1
            2021-12-10T12:00:00 1
            2022-01-15T09:00:00 1
            """
        );

        assertEquals(
            List.of("2021-12-02T23:30 1", "2021-12-10T12:00 1", "2022-01-15T09:00 1"),
            analytics.leastTrafficPeriod(records, 3).stream().map(TrafficRecord::asLine).toList()
        );
    }

    @Test
    void leastTrafficPeriodRequiresEnoughRecords() {
        assertThrows(
            IllegalArgumentException.class,
            () -> analytics.leastTrafficPeriod(SAMPLE_RECORDS.subList(0, 2), 3)
        );
    }

    private List<TrafficRecord> parseLines(String input) {
        return PARSER.parseLines(input.lines().toList());
    }
}
