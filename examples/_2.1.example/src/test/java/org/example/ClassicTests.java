package org.example;

import org.example.domain.Customer;
import org.example.domain.Product;
import org.example.domain.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassicTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.Shampoo, 5);


        assertTrue(success);
        assertEquals(5, store.getInventory(Product.Shampoo));
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.Shampoo, 15);


        assertFalse(success);
        assertEquals(10, store.getInventory(Product.Shampoo));
    }
}
