package com.trafficcounter.io;

import com.trafficcounter.domain.Record;
import java.util.List;

public interface RecordsParser {
    List<Record> parseLines(List<String> lines);
}
