package com.trafficcounter.report;

import com.trafficcounter.analytics.TrafficAnalytics;
import com.trafficcounter.domain.Record;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public final class TrafficReportFormatter {
    private TrafficReportFormatter() {}

    public static String format(List<Record> records) {
        StringBuilder out = new StringBuilder();
        out.append("Total cars: ").append(TrafficAnalytics.totalCars(records)).append("\n\n");

        out.append("Daily totals:\n");
        for (Map.Entry<LocalDate, Integer> entry : TrafficAnalytics.dailyTotals(records).entrySet()) {
            out.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }

        out.append("\nTop 3 half-hours:\n");
        for (Record record : TrafficAnalytics.topHalfHours(records, 3)) {
            out.append(record.asLine()).append("\n");
        }

        List<Record> period = TrafficAnalytics.leastTrafficPeriod(records, 3);
        out.append("\nLeast traffic 1.5-hour period (3 contiguous records):\n");
        for (Record record : period) {
            out.append(record.asLine()).append("\n");
        }
        out.append("Period total: ").append(TrafficAnalytics.totalCars(period));
        return out.toString();
    }
}
