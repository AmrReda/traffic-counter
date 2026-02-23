package com.trafficcounter.app;

import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.trafficcounter")
public class TrafficCounterApplication {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TrafficCounterApplication <input-file>");
            System.exit(1);
        }

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            TrafficCounterApplication.class
        )) {
            TrafficCounterRunner runner = context.getBean(TrafficCounterRunner.class);
            System.out.println(runner.run(args[0]));
        }
    }
}