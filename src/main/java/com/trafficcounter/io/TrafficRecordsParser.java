package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.util.List;

public interface TrafficRecordsParser {
    List<TrafficRecord> parseLines(List<String> lines);
}
