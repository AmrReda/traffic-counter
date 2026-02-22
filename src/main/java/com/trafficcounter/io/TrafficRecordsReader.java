package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.io.IOException;
import java.util.List;

public interface TrafficRecordsReader {
    List<TrafficRecord> read(String path) throws IOException;
}
