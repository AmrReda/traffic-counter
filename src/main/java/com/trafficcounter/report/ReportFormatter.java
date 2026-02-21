package com.trafficcounter.report;

import com.trafficcounter.domain.Record;
import java.util.List;

public interface ReportFormatter {
    String format(List<Record> records);
}
