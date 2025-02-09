package org.example.repository;

import org.example.model.Order;

import java.util.concurrent.ConcurrentHashMap;

public abstract class OrderRepository {
    private final ConcurrentHashMap<Integer, Order> orders;

    public OrderRepository() {
        this.orders = new ConcurrentHashMap<>();
    }

    public boolean addOrder(Order order) {
        return orders.putIfAbsent(order.getOrderNumber(), order) == null;
    }

    public boolean deleteOrderByOrderNumber(int orderId) {
        return orders.remove(orderId) == null;
    }

    public Order getOrderByOrderNumber(int orderId) {
        return orders.get(orderId);
    }
}
