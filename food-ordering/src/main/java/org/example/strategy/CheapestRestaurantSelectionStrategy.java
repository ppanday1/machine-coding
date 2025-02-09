package org.example.strategy;

import org.example.model.Item;
import org.example.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheapestRestaurantSelectionStrategy implements RestaurantSelectionStrategy {
    @Override
    public Restaurant getCheapestRestaurantForItem(List<Restaurant> restaurants, Item item) {
        Restaurant result = null;
        double minVal = Double.MAX_VALUE;
        for (Restaurant res : restaurants) {
            if (res.getPriceForItem(item) < minVal) {
                minVal = res.getPriceForItem(item);
                result = res;
            }
        }
        return result;
    }
}
