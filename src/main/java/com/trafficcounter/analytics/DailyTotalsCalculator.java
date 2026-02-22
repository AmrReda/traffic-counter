package com.trafficcounter.analytics;

import com.trafficcounter.domain.TrafficRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DailyTotalsCalculator {
    Map<LocalDate, Integer> dailyTotals(List<TrafficRecord> records);
}
