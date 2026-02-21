package com.trafficcounter.app;

import com.trafficcounter.analytics.TrafficAnalytics;
import com.trafficcounter.domain.Record;
import com.trafficcounter.io.FileRecordsReader;
import com.trafficcounter.io.IsoRecordParser;
import com.trafficcounter.io.RecordsParser;
import com.trafficcounter.io.RecordsReader;
import com.trafficcounter.report.ReportFormatter;
import com.trafficcounter.report.TrafficReportFormatter;
import java.io.IOException;
import java.util.List;

public class TrafficCounterApplication {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TrafficCounterApplication <input-file>");
            System.exit(1);
        }

        RecordsParser parser = new IsoRecordParser();
        RecordsReader reader = new FileRecordsReader(parser);
        TrafficAnalytics analytics = new TrafficAnalytics();
        ReportFormatter formatter = new TrafficReportFormatter(analytics, analytics, analytics, analytics);

        List<Record> records = reader.read(args[0]);
        System.out.println(formatter.format(records));
    }
}
