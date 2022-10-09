package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    private Store createStoreWithInventory(Product product, int count) {
        Store store = new Store();
        store.addInventory(product, count);
        return store;
    }

    private Customer createCustomer() {
        return new Customer();
    }

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();

        
        boolean success = sut.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();


        boolean success = sut.purchase(store, Product.SHAMPOO, 15);


        assertEquals(success, false);
        assertEquals(10, store.getInventory(Product.SHAMPOO));
    }
}