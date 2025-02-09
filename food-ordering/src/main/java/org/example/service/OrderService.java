package org.example.service;

import org.example.model.Item;
import org.example.model.Order;
import org.example.model.OrderDetail;
import org.example.model.Restaurant;
import org.example.repository.ItemRepository;
import org.example.repository.OngoingOrderRepository;
import org.example.repository.PastOrderRepository;
import org.example.repository.RestaurantRepository;
import org.example.strategy.CheapestRestaurantSelectionStrategy;
import org.example.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    RestaurantRepository restaurantRepository;
    OngoingOrderRepository ongoingOrderRepository;
    PastOrderRepository pastOrderRepository;
    ItemRepository itemRepository;

    RestaurantSelectionStrategy restaurantSelectionStrategy;

    private final AtomicInteger orderNumber;

    public OrderService(RestaurantRepository restaurantRepository,
                        OngoingOrderRepository ongoingOrderRepository,
                        PastOrderRepository pastOrderRepository,
                        ItemRepository itemRepository,
                        CheapestRestaurantSelectionStrategy orderSelectionStrategy) {
        this.restaurantRepository = restaurantRepository;
        this.ongoingOrderRepository = ongoingOrderRepository;
        this.pastOrderRepository = pastOrderRepository;
        this.itemRepository = itemRepository;
        this.restaurantSelectionStrategy = orderSelectionStrategy;
        orderNumber = new AtomicInteger(0);
    }

    public synchronized String placeOrder(String[] itemNames) {
        int n = itemNames.length;
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = itemRepository.getItemsByName(itemNames[i]);
        }
        List<OrderDetail> orderItems = new ArrayList<>();
        HashSet<Restaurant> allServingRest = new HashSet<>();
        double totalPrice = 0;
        for (Item item : items) {
            List<Restaurant> candidate = new ArrayList<>();
            for (Restaurant restaurant : restaurantRepository.getAllRestaurant()) {
                if (restaurant.getProcessingPower().get() != 0 && restaurant.servesItem(item)) {
                    candidate.add(restaurant);
                }
            }
            Restaurant result = restaurantSelectionStrategy.getCheapestRestaurantForItem(candidate, item);
            allServingRest.add(result);
            double price = result.getPriceForItem(item);
            orderItems.add(new OrderDetail(result, item, price));
            totalPrice += price;
        }
        if (orderItems.size() != n) {
            return "Not all orders could be served ";
        }
        Order order = new Order(orderNumber.decrementAndGet(), orderItems, totalPrice);
        ongoingOrderRepository.addOrder(order);

        //decrease the capcity of all restaurant
        for (Restaurant restaurant : allServingRest) {
            restaurant.placeOrder();
        }
        return order.toString();
    }

    public synchronized String fulFillOrder(int orderNumber) {
        Order order = ongoingOrderRepository.getOrderByOrderNumber(orderNumber);
        if (order == null) {
            return "No Such Order";
        }
        ongoingOrderRepository.deleteOrderByOrderNumber(orderNumber);
        pastOrderRepository.addOrder(order);

        HashSet<Restaurant> restaurants = new HashSet<>();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            restaurants.add(orderDetail.getRestaurant());
        }
        for (Restaurant restaurant : restaurants) {
            restaurant.replenish();
        }
        return "Order completed " + order;
    }
}
