package org.example.domain;

public class Customer {
    public boolean purchase(IStore store, Product product, int inventoryCount) {
        if (!store.hasEnoughInventory(product, inventoryCount)) {
            return false;
        }
        store.removeInventory(product, inventoryCount);
        return true;
    }
}
