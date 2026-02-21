package com.trafficcounter.io;

import com.trafficcounter.domain.Record;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class RecordParser {
    private RecordParser() {}

    public static List<Record> parseLines(List<String> lines) {
        List<Record> records = new ArrayList<>();
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            records.add(new Record(LocalDateTime.parse(parts[0]), Integer.parseInt(parts[1])));
        }
        return records;
    }
}
