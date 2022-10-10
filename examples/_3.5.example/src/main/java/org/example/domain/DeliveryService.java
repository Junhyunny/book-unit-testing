package org.example.domain;

import java.time.LocalDate;

public class DeliveryService {
    public boolean isDeliveryValid(Delivery delivery) {
        LocalDate now = LocalDate.now();
        return delivery.isValidDeliveryDate(now);
    }
}
