package com.trafficcounter.analytics;

import com.trafficcounter.domain.Record;
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
    public int totalCars(List<Record> records) {
        int total = 0;
        for (Record record : records) {
            total += record.count();
        }
        return total;
    }

    @Override
    public Map<LocalDate, Integer> dailyTotals(List<Record> records) {
        Map<LocalDate, Integer> totals = new LinkedHashMap<>();
        for (Record record : records) {
            LocalDate date = record.date();
            totals.put(date, totals.getOrDefault(date, 0) + record.count());
        }
        return totals;
    }

    @Override
    public List<Record> topHalfHours(List<Record> records, int topN) {
        List<Record> sorted = new ArrayList<>(records);
        sorted.sort(Comparator.comparingInt(Record::count).reversed().thenComparing(Record::timestamp));
        return sorted.subList(0, Math.min(topN, sorted.size()));
    }

    @Override
    public List<Record> leastTrafficPeriod(List<Record> records, int windowSize) {
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

    private int windowSum(List<Record> records, int start, int windowSize) {
        int sum = 0;
        for (int i = start; i < start + windowSize; i++) {
            sum += records.get(i).count();
        }
        return sum;
    }
}
