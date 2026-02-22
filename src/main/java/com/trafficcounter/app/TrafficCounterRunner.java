package com.trafficcounter.app;

import com.trafficcounter.domain.TrafficRecord;
import com.trafficcounter.io.TrafficRecordsReader;
import com.trafficcounter.report.ReportFormatter;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TrafficCounterRunner {
    private final TrafficRecordsReader recordsReader;
    private final ReportFormatter reportFormatter;

    public TrafficCounterRunner(TrafficRecordsReader recordsReader, ReportFormatter reportFormatter) {
        this.recordsReader = recordsReader;
        this.reportFormatter = reportFormatter;
    }

    public String run(String inputPath) throws IOException {
        List<TrafficRecord> records = recordsReader.read(inputPath);
        return reportFormatter.format(records);
    }
}
