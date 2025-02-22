package org.example.computationstrategy;

import org.example.models.Influencer;

public class FlatCommissionStrategy implements CommissionStrategy {
    @Override
    public double computeCommission(double amount, Influencer influencer) {
        return influencer.getShare();
    }
}
