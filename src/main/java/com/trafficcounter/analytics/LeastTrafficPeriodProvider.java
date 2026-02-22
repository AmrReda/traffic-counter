package com.trafficcounter.analytics;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface LeastTrafficPeriodProvider {
    List<TrafficRecord> leastTrafficPeriod(List<TrafficRecord> records, int windowSize);
}
