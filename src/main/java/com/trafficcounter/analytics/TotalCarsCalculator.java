package com.trafficcounter.analytics;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface TotalCarsCalculator {
    int totalCars(List<TrafficRecord> records);
}
