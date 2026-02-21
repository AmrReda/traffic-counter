package com.trafficcounter.io;

import com.trafficcounter.domain.Record;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileRecordsReader implements RecordsReader {
    private final RecordsParser parser;

    public FileRecordsReader(RecordsParser parser) {
        this.parser = parser;
    }

    @Override
    public List<Record> read(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        return parser.parseLines(lines);
    }
}
