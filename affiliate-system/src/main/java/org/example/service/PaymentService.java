package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.computationstrategy.CommissionStrategyFactory;
import org.example.models.Influencer;
import org.example.models.Order;
import org.example.models.ShareModel;
import org.example.respository.InfluencerRepository;
import org.example.respository.OngoingOrderRepository;
import org.example.respository.PastOrderRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {
    PastOrderRepository pastOrderRepository;
    OngoingOrderRepository ongoingOrderRepository;
    InfluencerRepository influencerRepository;

    public PaymentService(PastOrderRepository pastOrderRepository,
                          OngoingOrderRepository ongoingOrderRepository,
                          InfluencerRepository influencerRepository) {
        this.pastOrderRepository = pastOrderRepository;
        this.ongoingOrderRepository = ongoingOrderRepository;
        this.influencerRepository = influencerRepository;
    }

    public synchronized void makePayment(String orderId) {
        try {
            Order ongoingOrder = ongoingOrderRepository.getOrder(orderId);
            Influencer influencer = influencerRepository.getInfluencerByName(ongoingOrder.getInfluencerId());
            ongoingOrderRepository.removeOrder(orderId);
            pastOrderRepository.saveOrder(ongoingOrder);
            influencer.makePayment(findCommission(ongoingOrder.getPrice(), influencer));
        } catch (Exception e) {
            log.error("Error Occurred with making payment");
        }
    }

    private double findCommission(double price, Influencer influencer) {
        return CommissionStrategyFactory.getStrategy(influencer.getShareModel()).computeCommission(price, influencer);
    }
}
