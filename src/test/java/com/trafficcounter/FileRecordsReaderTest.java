package com.trafficcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trafficcounter.domain.TrafficRecord;
import com.trafficcounter.io.FileRecordsReader;
import com.trafficcounter.io.IsoRecordParser;
import com.trafficcounter.io.RecordsReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileRecordsReaderTest {
    private static final String FILE_CONTENT = """
        2021-12-01T05:00:00 5
        2021-12-01T05:30:00 12

        2021-12-01T06:00:00 14
        """;

    private final RecordsReader reader = new FileRecordsReader(new IsoRecordParser());

    @TempDir
    Path tempDir;

    @Test
    void readsAndParsesRecordsFromFile() throws IOException {
        Path input = tempDir.resolve("traffic.txt");
        Files.writeString(input, FILE_CONTENT);

        List<TrafficRecord> records = reader.read(input.toString());

        assertEquals(3, records.size());
        assertEquals("2021-12-01T05:00 5", records.get(0).asLine());
        assertEquals("2021-12-01T05:30 12", records.get(1).asLine());
        assertEquals("2021-12-01T06:00 14", records.get(2).asLine());
    }
}
