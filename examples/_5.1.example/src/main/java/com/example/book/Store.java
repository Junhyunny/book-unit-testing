package com.example.book;

public interface Store {

    boolean hasEnoughInventory(Product product, int count);

    void removeInventory(Product product, int count);
}
