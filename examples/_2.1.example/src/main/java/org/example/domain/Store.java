package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Store implements IStore {

    private final Map<Product, Integer> inventory;

    public Store() {
        this.inventory = new HashMap<>();
    }

    private int subtractInventory(Product product, int inventoryCount) {
        return this.getInventory(product) - inventoryCount;
    }

    @Override
    public void addInventory(Product product, int inventoryCount) {
        this.inventory.put(product, inventoryCount);
    }

    @Override
    public int getInventory(Product product) {
        return this.inventory.get(product);
    }

    @Override
    public void removeInventory(Product product, int inventoryCount) {
        int remainInventoryCount = subtractInventory(product, inventoryCount);
        this.inventory.put(product, remainInventoryCount);
    }

    @Override
    public boolean hasEnoughInventory(Product product, int inventoryCount) {
        return subtractInventory(product, inventoryCount) >= 0;
    }
}
