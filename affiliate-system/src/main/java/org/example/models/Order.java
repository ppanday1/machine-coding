package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String orderId;
    private String userId;
    private String influencerId;
    private boolean throughInfluencer;
    private double price;
    private List<Item> items;
}
