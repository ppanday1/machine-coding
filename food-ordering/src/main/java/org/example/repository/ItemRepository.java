package org.example.repository;

import org.example.model.Item;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final ConcurrentHashMap<String, Item> items;

    public ItemRepository() {
        this.items = new ConcurrentHashMap<>();
    }

    public Item getItemsByName(String itemName) {
        return items.getOrDefault(itemName, null);
    }

    public boolean putItemByName(String itemName) {
        return items.putIfAbsent(itemName, new Item(itemName)) == null;
    }
}
