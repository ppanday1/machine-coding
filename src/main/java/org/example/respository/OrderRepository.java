package org.example.respository;

import org.example.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final ConcurrentHashMap<String, HashSet<Order>> activeOrders;
    private final ConcurrentHashMap<String, HashSet<Order>> pastOrders;

    public OrderRepository() {
        activeOrders = new ConcurrentHashMap<>();
        pastOrders = new ConcurrentHashMap<>();
    }

    public boolean addToActiveOrder(String userId, Order order) {
        if (!activeOrders.containsKey(userId)) {
            activeOrders.put(userId, new HashSet<>());
        }
        HashSet<Order> ordersForUser = activeOrders.get(userId);
        ordersForUser.add(order);
        return true;
    }

    public boolean addToPastOrder(String userId, Order order) {
        if (!pastOrders.containsKey(userId)) {
            pastOrders.put(userId, new HashSet<>());
        }
        HashSet<Order> ordersForUser = pastOrders.get(userId);
        ordersForUser.add(order);
        return true;
    }

    public List<Order> getAllActiveOrdersForUser(String userId) {
        return new ArrayList<>(activeOrders.getOrDefault(userId, new HashSet<>()));
    }

    public List<Order> getAllPastOrdersForUser(String userId) {
        return new ArrayList<>(pastOrders.getOrDefault(userId, new HashSet<>()));
    }

    public boolean removeFromActiveOrder(String userId, Order order) {
        if (!activeOrders.containsKey(userId)) {
            return false;
        }
        HashSet<Order> orders = activeOrders.get(userId);
        orders.remove(order);
        return true;
    }
}
