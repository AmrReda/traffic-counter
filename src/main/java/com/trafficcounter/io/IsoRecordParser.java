package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IsoRecordParser implements RecordsParser {
    @Override
    public List<TrafficRecord> parseLines(List<String> lines) {
        List<TrafficRecord> records = new ArrayList<>();
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            records.add(new TrafficRecord(LocalDateTime.parse(parts[0]), Integer.parseInt(parts[1])));
        }
        return records;
    }
}
