package org.example.repository;

import org.example.model.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ItemRepository {
    private HashMap<String, Item> items;

    public ItemRepository() {
        this.items = new HashMap<>();
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
