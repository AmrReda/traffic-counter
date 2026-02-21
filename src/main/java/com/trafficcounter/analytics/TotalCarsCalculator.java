package com.trafficcounter.analytics;

import com.trafficcounter.domain.Record;
import java.util.List;

public interface TotalCarsCalculator {
    int totalCars(List<Record> records);
}
