package org.example.domain;

public interface IStore {

    void addInventory(Product product, int inventoryCount);

    int getInventory(Product product);

    boolean hasEnoughInventory(Product product, int inventoryCount);

    void removeInventory(Product product, int inventoryCount);
}
