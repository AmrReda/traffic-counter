package com.trafficcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.trafficcounter.domain.TrafficRecord;
import com.trafficcounter.io.IsoTrafficRecordParser;
import java.util.List;
import org.junit.jupiter.api.Test;

class IsoTrafficRecordParserTest {
    private final IsoTrafficRecordParser parser = new IsoTrafficRecordParser();

    @Test
    void parseRejectsNullLines() {
        assertThrows(NullPointerException.class, () -> parser.parseLines(null));
    }

    @Test
    void parseRejectsMalformedLine() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parseLines(List.of("2021-12-01T05:00:00 5 extra"))
        );
        assertTrue(ex.getMessage().contains("line 1"));
    }

    @Test
    void parseRejectsInvalidTimestamp() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parseLines(List.of("not-a-date 5"))
        );
        assertTrue(ex.getMessage().contains("line 1"));
    }

    @Test
    void parseRejectsInvalidCount() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parseLines(List.of("2021-12-01T05:00:00 five"))
        );
        assertTrue(ex.getMessage().contains("line 1"));
    }

    @Test
    void parseSupportsBlankLines() {
        List<TrafficRecord> records = parser.parseLines(
            List.of("2021-12-01T05:00:00 5", "   ", "2021-12-01T05:30:00 7")
        );
        assertEquals(2, records.size());
        assertEquals("2021-12-01T05:00 5", records.get(0).asLine());
        assertEquals("2021-12-01T05:30 7", records.get(1).asLine());
    }
}
