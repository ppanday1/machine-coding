package org.example.computationstrategy;

import org.example.models.Influencer;

public interface CommissionStrategy {
    double computeCommission(double amount, Influencer influencer);
}
