package org.example.computationstrategy;

import org.example.models.ShareModel;

public class CommissionStrategyFactory {
    public static CommissionStrategy getStrategy(ShareModel shareModel) {
        if (shareModel == ShareModel.FLAT) {
            return new FlatCommissionStrategy();
        } else if (shareModel == ShareModel.PERCENTAGE) {
            return new PercentageCommissionStrategy();
        }
        throw new IllegalArgumentException("Unknown share model: " + shareModel);
    }
}
