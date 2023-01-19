package com.example.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationTests {

    @Test
    void creating_a_report() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.getNumberOfUsers()).thenReturn(10);


        ReportService sut = new DefaultReportService(userRepository);
        Report report = sut.createReport();


        assertThat(report.getNumberOfUser()).isEqualTo(10);
        verify(userRepository, times(1)).getNumberOfUsers();
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {
        Store store = Mockito.mock(Store.class);
        when(store.hasEnoughInventory(Product.SHAMPOO, 5)).thenReturn(false);


        Customer sut = new Customer();
        boolean success = sut.purchase(store, Product.SHAMPOO, 5);


        assertThat(success).isFalse();
        verify(store, times(1)).removeInventory(Product.SHAMPOO, 5);
    }
}
