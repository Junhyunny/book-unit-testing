package com.example.book;

public class Customer {

    public boolean purchase(Store store, Product product, int count) {
        boolean result = store.hasEnoughInventory(product, count);
        if (!result) {
            return false;
        }
        store.removeInventory(product, count);
        return true;
    }
}
