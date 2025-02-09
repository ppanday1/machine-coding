package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Order {
    private final int orderNumber;
    private List<OrderDetail> orderDetails;
    private double totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalPrice, totalPrice) == 0 && orderDetails.equals(order.orderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetails, totalPrice);
    }
}
