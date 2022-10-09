package org.example.domain;

public class Customer {
    public boolean purchase(Store store, Product product, int count) {
        return store.removeInventory(product, count);
    }

    public boolean purchaseWithoutRemoveInventory(Store store, Product product, int count) {
        return store.hasEnoughInventory(product, count);
    }
}
