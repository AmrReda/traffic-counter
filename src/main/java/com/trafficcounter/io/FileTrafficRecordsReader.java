package com.trafficcounter.io;

import com.trafficcounter.domain.TrafficRecord;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class FileTrafficRecordsReader implements TrafficRecordsReader {
    private final TrafficRecordsParser parser;

    public FileTrafficRecordsReader(TrafficRecordsParser parser) {
        this.parser = Objects.requireNonNull(parser, "parser must not be null");
    }

    @Override
    public List<TrafficRecord> read(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("path must not be null or blank");
        }

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            return parser.parseLines(lines);
        } catch (IOException ex) {
            throw new IOException("Failed to read traffic records from: " + path, ex);
        }
    }
}
