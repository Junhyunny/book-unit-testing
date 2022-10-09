package org.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    Store store;
    Customer customer;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        customer = new Customer();
    }

    @Test
    void purchase_succeeds_when_enough_inventory() {

        boolean success = customer.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }

    @Test
    void purchase_succeeds_when_enough_inventory_() {
        boolean success = customer.purchaseWithoutRemoveInventory(store, Product.SHAMPOO, 5);
        store.removeInventory(success, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }
}