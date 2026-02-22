package com.trafficcounter.report;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface ReportFormatter {
    String format(List<TrafficRecord> records);
}
