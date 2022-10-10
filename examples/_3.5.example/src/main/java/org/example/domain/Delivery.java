package org.example.domain;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Delivery {

    private LocalDate startDate;

    public Delivery(LocalDate localDate) {
        this.startDate = localDate;
    }

    public boolean isValidDeliveryDate(LocalDate today) {
        long daysBetween = DAYS.between(today, startDate);
        return daysBetween >= 2;
    }
}
