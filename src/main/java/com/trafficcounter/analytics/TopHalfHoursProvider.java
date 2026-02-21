package com.trafficcounter.analytics;

import com.trafficcounter.domain.Record;
import java.util.List;

public interface TopHalfHoursProvider {
    List<Record> topHalfHours(List<Record> records, int topN);
}
