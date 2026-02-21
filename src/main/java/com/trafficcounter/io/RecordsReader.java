package com.trafficcounter.io;

import com.trafficcounter.domain.Record;
import java.io.IOException;
import java.util.List;

public interface RecordsReader {
    List<Record> read(String path) throws IOException;
}
