package org.example.repository;

import org.example.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RestaurantRepository {
    private final ConcurrentHashMap<String, Restaurant> restaurants;

    public RestaurantRepository() {
        this.restaurants = new ConcurrentHashMap<>();
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurants.getOrDefault(name, null);
    }

    public boolean addRestaurant(Restaurant restaurant) {
        return restaurants.putIfAbsent(restaurant.getName(), restaurant) == null;
    }

    public List<Restaurant> getAllRestaurant() {
        return restaurants.values().stream().toList();
    }
}
