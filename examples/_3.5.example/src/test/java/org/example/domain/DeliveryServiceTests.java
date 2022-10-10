package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

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

    @Test
    void delivery_with_past_date_should_be_considered_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_past_date_should_be_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_past_date_is_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }


    @Test
    void delivery_with_a_past_date_is_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void detect_an_invalid_delivery_date(int daysFromNow) {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().plusDays(daysFromNow);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void the_soonest_delivery_date_is_two_days_from_now() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().plusDays(2);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, true);
    }
}