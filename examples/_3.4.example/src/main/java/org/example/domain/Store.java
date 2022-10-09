package org.example.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private Map<Product, Integer> inventory = new ConcurrentHashMap<>();

    public void addInventory(Product product, int count) {
        inventory.merge(product, count, Integer::sum);
    }

    public int getInventory(Product product) {
        return inventory.get(product);
    }


    public boolean hasEnoughInventory(Product product, int count) {
        return inventory.containsKey(product) && inventory.get(product) >= count;
    }

    public void removeInventory(boolean success, Product product, int count) {
        if (success) {
            removeInventory(product, count);
        }
    }

    public boolean removeInventory(Product product, int count) {
        if (!hasEnoughInventory(product, count)) {
            return false;
        }
        inventory.merge(product, count, (oldValue, newValue) -> oldValue - newValue);
        return true;
    }
}
