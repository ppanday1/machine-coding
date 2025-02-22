package org.example.computationstrategy;

import org.example.models.Influencer;

public class PercentageCommissionStrategy implements CommissionStrategy {
    public double computeCommission(double amount, Influencer influencer) {

        return (amount * influencer.getShare()) / 100.0;
    }
}
