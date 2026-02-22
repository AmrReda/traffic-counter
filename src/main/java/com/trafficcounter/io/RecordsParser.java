package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface RecordsParser {
    List<TrafficRecord> parseLines(List<String> lines);
}
