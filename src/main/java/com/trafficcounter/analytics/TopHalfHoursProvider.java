package com.trafficcounter.analytics;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface TopHalfHoursProvider {
    List<TrafficRecord> topHalfHours(List<TrafficRecord> records, int topN);
}
