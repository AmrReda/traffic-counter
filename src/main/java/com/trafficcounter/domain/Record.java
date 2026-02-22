package com.trafficcounter.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Record(LocalDateTime timestamp, int count) {
    public LocalDate date() {
        return timestamp.toLocalDate();
    }

    // Keeps output aligned with the input line format: "<ISO timestamp> <count>".
    public String asLine() {
        return timestamp + " " + count;
    }
}
