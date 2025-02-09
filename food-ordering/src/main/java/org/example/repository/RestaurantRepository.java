package org.example.repository;

import org.example.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class RestaurantRepository {
    private final HashMap<String, Restaurant> restaurants;

    public RestaurantRepository() {
        this.restaurants = new HashMap<>();
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurants.getOrDefault(name, null);
    }

    public boolean addRestaurant(Restaurant restaurant) {
        if (restaurants.containsKey(restaurant.getName())) {
            return false;
        }
        restaurants.put(restaurant.getName(), restaurant);
        return true;
    }

    public List<Restaurant> getAllRestaurant() {
        return restaurants.values().stream().toList();
    }
}
