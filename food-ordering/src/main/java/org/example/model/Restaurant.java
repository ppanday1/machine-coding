package org.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class Restaurant {
    private String name;
    private AtomicInteger processingPower;
    private final HashMap<Item, Double> menu;

    public Restaurant(String name, Item[] items, double[] price, int processingPower) {
        this.name = name;
        this.processingPower = new AtomicInteger(processingPower);
        menu = new HashMap<>();
        int n = items.length;
        for (int i = 0; i < n; i++) {
            menu.put(items[i], price[i]);
        }
    }

    public void placeOrder() {
        processingPower.decrementAndGet();
    }

    public void replenish() {
        processingPower.incrementAndGet();
    }

    public void updateMenu(Item[] items, double[] prices, int capacity) {
        int n = items.length;
        for (int i = 0; i < n; i++) {
            menu.put(items[i], prices[i]);
        }
        processingPower = new AtomicInteger(capacity);
    }

    public boolean servesItem(Item item) {
        return menu.containsKey(item);
    }

    public double getPriceForItem(Item item) {
        return menu.get(item);
    }
}
