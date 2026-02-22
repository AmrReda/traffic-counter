package com.trafficcounter.report;

import com.trafficcounter.analytics.DailyTotalsCalculator;
import com.trafficcounter.analytics.LeastTrafficPeriodProvider;
import com.trafficcounter.analytics.TopHalfHoursProvider;
import com.trafficcounter.analytics.TotalCarsCalculator;
import com.trafficcounter.domain.TrafficRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TrafficReportFormatter implements ReportFormatter {
    private final TotalCarsCalculator totalCarsCalculator;
    private final DailyTotalsCalculator dailyTotalsCalculator;
    private final TopHalfHoursProvider topHalfHoursProvider;
    private final LeastTrafficPeriodProvider leastTrafficPeriodProvider;

    public TrafficReportFormatter(
        TotalCarsCalculator totalCarsCalculator,
        DailyTotalsCalculator dailyTotalsCalculator,
        TopHalfHoursProvider topHalfHoursProvider,
        LeastTrafficPeriodProvider leastTrafficPeriodProvider
    ) {
        this.totalCarsCalculator = totalCarsCalculator;
        this.dailyTotalsCalculator = dailyTotalsCalculator;
        this.topHalfHoursProvider = topHalfHoursProvider;
        this.leastTrafficPeriodProvider = leastTrafficPeriodProvider;
    }

    @Override
    public String format(List<TrafficRecord> records) {
        StringBuilder out = new StringBuilder();
        out.append("Total cars: ").append(totalCarsCalculator.totalCars(records)).append("\n\n");

        out.append("Daily totals:\n");
        for (Map.Entry<LocalDate, Integer> entry : dailyTotalsCalculator.dailyTotals(records).entrySet()) {
            out.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }

        out.append("\nTop 3 half-hours:\n");
        for (TrafficRecord record : topHalfHoursProvider.topHalfHours(records, 3)) {
            out.append(record.asLine()).append("\n");
        }

        List<TrafficRecord> period = leastTrafficPeriodProvider.leastTrafficPeriod(records, 3);
        out.append("\nLeast traffic 1.5-hour period (3 contiguous records):\n");
        for (TrafficRecord record : period) {
            out.append(record.asLine()).append("\n");
        }
        out.append("Period total: ").append(totalCarsCalculator.totalCars(period));
        return out.toString();
    }
}
