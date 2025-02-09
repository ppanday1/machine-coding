package org.example.repository;

import org.example.model.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private ConcurrentHashMap<String, Item> items;

    public ItemRepository() {
        this.items = new ConcurrentHashMap<>();
    }

    public Item getItemsByName(String itemName){
        return items.getOrDefault(itemName, null);
    }

    public boolean putItemByName(String itemName){
        if(!items.containsKey(itemName)){
            items.put(itemName,new Item(itemName));
            return true;
        }
        return false;
    }
}
