package org.example.strategy;

import org.example.model.Item;
import org.example.model.Restaurant;

import java.util.List;

public interface OrderSelectionStrategy {
    Restaurant getCheapestRestaurantForItem(List<Restaurant>restaurants, Item item);
}
