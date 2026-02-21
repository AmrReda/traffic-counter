package com.trafficcounter.analytics;

import com.trafficcounter.domain.Record;
import java.util.List;

public interface LeastTrafficPeriodProvider {
    List<Record> leastTrafficPeriod(List<Record> records, int windowSize);
}
