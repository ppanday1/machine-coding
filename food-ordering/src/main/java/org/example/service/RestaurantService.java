package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Item;
import org.example.model.Restaurant;
import org.example.repository.ItemRepository;
import org.example.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             ItemRepository itemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
    }

    public boolean onboardRestaurant(String name, String[] itemName, double[] prices, int capacity) {
        int len = itemName.length;
        Item[] items = new Item[len];
        for (int i = 0; i < len; i++) {
            items[i] = itemRepository.getItemsByName(itemName[i]);
        }
        Restaurant restaurant = new Restaurant(name, items, prices, capacity);
        return restaurantRepository.addRestaurant(restaurant);
    }

    public boolean updateMenu(String name, String[] itemName, double[] prices, int capacity) {
        Restaurant restaurant = restaurantRepository.getRestaurantByName(name);
        if (restaurant == null) {
            log.error("Restaurant doesn't exist {}", name);
            return false;
        }
        int len = itemName.length;
        Item[] items = new Item[len];
        for (int i = 0; i < len; i++) {
            items[i] = itemRepository.getItemsByName(itemName[i]);
        }
        restaurant.updateMenu(items, prices, capacity);
        return true;
    }

    public void printSystemStats() {
        List<Restaurant> restaurants = restaurantRepository.getAllRestaurant();
        for (Restaurant restaurant : restaurants) {
            log.info("{} has capacity {}", restaurant.getName(), restaurant.getProcessingPower());
        }
    }
}
