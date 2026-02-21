package com.trafficcounter.app;

import com.trafficcounter.domain.Record;
import com.trafficcounter.io.RecordFileReader;
import com.trafficcounter.report.TrafficReportFormatter;
import java.io.IOException;
import java.util.List;

public class TrafficCounterApplication {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TrafficCounterApplication <input-file>");
            System.exit(1);
        }

        List<Record> records = RecordFileReader.read(args[0]);
        System.out.println(TrafficReportFormatter.format(records));
    }
}
