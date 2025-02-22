package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Item;
import org.example.respository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public synchronized void addItem(String name, double price) {
        try {
            Item item = new Item(name, price);
            itemRepository.addItem(item);
        } catch (Exception e) {
            log.error("error occurred with adding item {} , {}", name, e);
        }
    }

    public synchronized Item getItem(String name) {
        try {
            return itemRepository.getItemByName(name);
        } catch (Exception e) {
            log.error("error occurred with adding item {} , {}", name, e);
        }
        return null;
    }

}
