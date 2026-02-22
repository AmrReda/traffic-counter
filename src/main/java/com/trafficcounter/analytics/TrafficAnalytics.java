package com.trafficcounter.analytics;

import com.trafficcounter.domain.TrafficRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrafficAnalytics implements
    TotalCarsCalculator,
    DailyTotalsCalculator,
    TopHalfHoursProvider,
    LeastTrafficPeriodProvider {
    @Override
    public int totalCars(List<TrafficRecord> records) {
        int total = 0;
        for (TrafficRecord record : records) {
            total += record.count();
        }
        return total;
    }

    @Override
    public Map<LocalDate, Integer> dailyTotals(List<TrafficRecord> records) {
        Map<LocalDate, Integer> totals = new LinkedHashMap<>();
        for (TrafficRecord record : records) {
            LocalDate date = record.date();
            totals.put(date, totals.getOrDefault(date, 0) + record.count());
        }
        return totals;
    }

    @Override
    public List<TrafficRecord> topHalfHours(List<TrafficRecord> records, int topN) {
        List<TrafficRecord> sorted = new ArrayList<>(records);
        sorted.sort(Comparator.comparingInt(TrafficRecord::count).reversed().thenComparing(TrafficRecord::timestamp));
        return sorted.subList(0, Math.min(topN, sorted.size()));
    }

    @Override
    public List<TrafficRecord> leastTrafficPeriod(List<TrafficRecord> records, int windowSize) {
        if (records.size() < windowSize) {
            throw new IllegalArgumentException("Need at least " + windowSize + " records.");
        }

        int bestStart = 0;
        int bestSum = windowSum(records, 0, windowSize);

        for (int start = 1; start <= records.size() - windowSize; start++) {
            int currentSum = windowSum(records, start, windowSize);
            if (currentSum < bestSum) {
                bestSum = currentSum;
                bestStart = start;
            }
        }

        return new ArrayList<>(records.subList(bestStart, bestStart + windowSize));
    }

    private int windowSum(List<TrafficRecord> records, int start, int windowSize) {
        int sum = 0;
        for (int i = start; i < start + windowSize; i++) {
            sum += records.get(i).count();
        }
        return sum;
    }
}
