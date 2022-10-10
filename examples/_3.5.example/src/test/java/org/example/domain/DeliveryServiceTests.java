package org.example.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryServiceTests {

    @Test
    void isDeliveryValid_invalidDate_returnsFalse() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_invalid_date_should_be_considered_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }
}