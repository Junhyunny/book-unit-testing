package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }

    @Test
    void purchase_succeeds_when_enough_inventory_with_two_times_assert() {

        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();


        boolean success = customer.purchaseWithoutRemoveInventory(store, Product.SHAMPOO, 5);
        store.removeInventory(success, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }
}