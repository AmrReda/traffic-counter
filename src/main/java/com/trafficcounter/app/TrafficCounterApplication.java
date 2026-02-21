package com.trafficcounter.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrafficCounterApplication {
    public record Record(LocalDateTime timestamp, int count) {
        public String asLine() {
            return timestamp + " " + count;
        }
    }

    public static List<Record> readRecords(String filePath) throws IOException {
        List<Record> records = new ArrayList<>();
        for (String rawLine : Files.readAllLines(Path.of(filePath))) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            records.add(new Record(LocalDateTime.parse(parts[0]), Integer.parseInt(parts[1])));
        }
        return records;
    }

    public static int totalCars(List<Record> records) {
        int total = 0;
        for (Record record : records) {
            total += record.count();
        }
        return total;
    }

    public static Map<LocalDate, Integer> dailyTotals(List<Record> records) {
        Map<LocalDate, Integer> totals = new LinkedHashMap<>();
        for (Record record : records) {
            LocalDate date = record.timestamp().toLocalDate();
            totals.put(date, totals.getOrDefault(date, 0) + record.count());
        }
        return totals;
    }

    public static List<Record> topHalfHours(List<Record> records, int topN) {
        List<Record> sorted = new ArrayList<>(records);
        sorted.sort(Comparator.comparingInt(Record::count).reversed().thenComparing(Record::timestamp));
        return sorted.subList(0, Math.min(topN, sorted.size()));
    }

    public static List<Record> leastTrafficPeriod(List<Record> records, int windowSize) {
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

    private static int windowSum(List<Record> records, int start, int windowSize) {
        int sum = 0;
        for (int i = start; i < start + windowSize; i++) {
            sum += records.get(i).count();
        }
        return sum;
    }

    public static String formatReport(List<Record> records) {
        StringBuilder out = new StringBuilder();
        out.append("Total cars: ").append(totalCars(records)).append("\n\n");

        out.append("Daily totals:\n");
        for (Map.Entry<LocalDate, Integer> entry : dailyTotals(records).entrySet()) {
            out.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }

        out.append("\nTop 3 half-hours:\n");
        for (Record record : topHalfHours(records, 3)) {
            out.append(record.asLine()).append("\n");
        }

        List<Record> period = leastTrafficPeriod(records, 3);
        out.append("\nLeast traffic 1.5-hour period (3 contiguous records):\n");
        for (Record record : period) {
            out.append(record.asLine()).append("\n");
        }
        out.append("Period total: ").append(totalCars(period));
        return out.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TrafficCounterApplication <input-file>");
            System.exit(1);
        }
        List<Record> records = readRecords(args[0]);
        System.out.println(formatReport(records));
    }
}
