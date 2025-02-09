package org.example.repository;

import org.example.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public class PastOrderRepository {
    private final HashSet<Order> orders;

    public PastOrderRepository() {
        this.orders = new HashSet<>();
    }

    public boolean addOrder(Order order) {
        if (orders.contains(order)) {
            return false;
        }
        orders.add(order);
        return true;
    }

    public boolean removeOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderId) {
                orders.remove(order);
                return true;
            }
        }
        return false;
    }

    public Order getOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderId) {
                return order;
            }
        }
        return null;
    }
}
