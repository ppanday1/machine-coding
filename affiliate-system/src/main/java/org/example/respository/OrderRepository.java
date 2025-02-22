package org.example.respository;

import org.example.exception.ElementAlreadyExistException;
import org.example.exception.ElementDoesNotExistException;
import org.example.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public abstract class OrderRepository {

    private final ConcurrentHashMap<String, Order> orders;

    public OrderRepository() {
        orders = new ConcurrentHashMap<>();
    }

    public Order getOrder(String orderId) throws ElementDoesNotExistException {
        if (!orders.containsKey(orderId)) {
            throw new ElementDoesNotExistException("order with orderId " + orderId + " doesn't exists");
        }
        return orders.get(orderId);
    }

    public void saveOrder(Order order) throws ElementAlreadyExistException {
        if (orders.containsKey(order.getOrderId())) {
            throw new ElementAlreadyExistException("order with orderId " + order.getOrderId() + " already exists");
        }
        orders.put(order.getOrderId(), order);
    }

    public List<Order> getOrderForInfluencer(String influencerId) {
        List<Order> list = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getInfluencerId().equals(influencerId)) {
                list.add(order);
            }
        }
        return list;
    }

    public void removeOrder(String orderId) throws ElementDoesNotExistException {
        if (!orders.containsKey(orderId)) {
            throw new ElementDoesNotExistException("order with orderId " + orderId + " doesn't exists");
        }
        orders.remove(orderId);
    }
}
