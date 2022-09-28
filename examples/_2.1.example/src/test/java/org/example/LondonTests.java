package org.example;

import org.example.domain.Customer;
import org.example.domain.IStore;
import org.example.domain.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LondonTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        IStore store = Mockito.mock(IStore.class);
        when(store.hasEnoughInventory(Product.Shampoo, 5)).thenReturn(true);


        Customer customer = new Customer();
        boolean success = customer.purchase(store, Product.Shampoo, 5);


        assertTrue(success);
        verify(store, times(1)).removeInventory(Product.Shampoo, 5);
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        IStore store = Mockito.mock(IStore.class);
        when(store.hasEnoughInventory(Product.Shampoo, 15)).thenReturn(false);


        Customer customer = new Customer();
        boolean success = customer.purchase(store, Product.Shampoo, 15);


        assertFalse(success);
        verify(store, times(0)).removeInventory(Product.Shampoo, 5);
    }
}
