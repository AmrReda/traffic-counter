package com.trafficcounter.io;

import com.trafficcounter.domain.Record;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class RecordFileReader {
    private RecordFileReader() {}

    public static List<Record> read(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        return RecordParser.parseLines(lines);
    }
}
