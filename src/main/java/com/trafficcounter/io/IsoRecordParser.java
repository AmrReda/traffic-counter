package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IsoRecordParser implements RecordsParser {
    @Override
    public List<TrafficRecord> parseLines(List<String> lines) {
        Objects.requireNonNull(lines, "lines must not be null");
        List<TrafficRecord> records = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String rawLine = lines.get(i);
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid record format at line " + (i + 1) + ": " + line);
            }
            try {
                records.add(new TrafficRecord(LocalDateTime.parse(parts[0]), Integer.parseInt(parts[1])));
            } catch (DateTimeException | NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid record values at line " + (i + 1) + ": " + line, ex);
            }
        }
        return records;
    }
}
