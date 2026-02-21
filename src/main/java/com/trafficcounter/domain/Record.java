package com.trafficcounter.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Record(LocalDateTime timestamp, int count) {
    public LocalDate date() {
        return timestamp.toLocalDate();
    }

    public String asLine() {
        return timestamp + " " + count;
    }
}
