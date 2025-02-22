package org.example.respository;

import org.example.exception.ElementAlreadyExistException;
import org.example.exception.ElementDoesNotExistException;
import org.example.models.Item;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final ConcurrentHashMap<String, Item> items;

    public ItemRepository() {
        items = new ConcurrentHashMap<>();
    }

    public void addItem(Item item) throws ElementAlreadyExistException {
        if (items.containsKey(item.getName())) {
            throw new ElementAlreadyExistException("Item already " + item.getName() + " exists");
        }
        items.put(item.getName(), item);
    }

    public Item getItemByName(String name) throws ElementDoesNotExistException {
        if (!items.containsKey(name)) {
            throw new ElementDoesNotExistException("Item " + name + " does not exists");
        }
        return items.get(name);
    }

}
