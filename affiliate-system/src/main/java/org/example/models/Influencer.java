package org.example.models;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

@Data
public class Influencer {
    private String name;
    private ShareModel shareModel;
    private double share;
    private double totalPaidAmount;
    private double totalDueAmount;

    public synchronized void makePayment(double amount) {
        totalPaidAmount+=amount;
        totalDueAmount-=amount;
    }

    public synchronized void addCommission(double amount) {
        totalDueAmount+=amount;
    }

    public void removeCommission(double amount) {
        totalDueAmount-=amount;
    }
}
