package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.computationstrategy.CommissionStrategy;
import org.example.computationstrategy.CommissionStrategyFactory;
import org.example.models.*;
import org.example.respository.InfluencerRepository;
import org.example.respository.ItemRepository;
import org.example.respository.LinkRepository;
import org.example.respository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final LinkRepository linkRepository;
    private final InfluencerRepository influencerRepository;
    private final OrderRepository ongoingOrderRepository;
    private final OrderRepository pastOrderRepository;
    private final ItemRepository itemRepository;

    public OrderService(LinkRepository linkRepository,
                        InfluencerRepository influencerRepository,
                        OrderRepository ongoingOrderRepository,
                        OrderRepository pastOrderRepository,
                        ItemRepository itemRepository) {
        this.linkRepository = linkRepository;
        this.influencerRepository = influencerRepository;
        this.ongoingOrderRepository = ongoingOrderRepository;
        this.pastOrderRepository = pastOrderRepository;
        this.itemRepository = itemRepository;
    }

    public synchronized String placeOrder(String link) {
        try {
            AffiliateLink affiliateLink = linkRepository.getAffiliateLinkByName(link);
            Influencer influencer = influencerRepository.getInfluencerByName(affiliateLink.getInfluencerId());
            Item item = itemRepository.getItemByName(affiliateLink.getItemId());
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setInfluencerId(influencer.getName());
            order.setThroughInfluencer(true);
            order.setPrice(item.getPrice());
            order.setItems(List.of(item));
            ongoingOrderRepository.saveOrder(order);
            influencer.addCommission(findCommission(order.getPrice(), influencer));
            return order.getOrderId();
        } catch (Exception e) {
            log.error("Error occurred while making order ", e);
        }
        return null;
    }

    private double findCommission(double price, Influencer influencer) {
        return CommissionStrategyFactory.getStrategy(influencer.getShareModel()).computeCommission(price, influencer);
    }

    public synchronized void returnOrder(String orderId) {
        try {
            Order order = ongoingOrderRepository.getOrder(orderId);
            if (order.isThroughInfluencer()) {
                ongoingOrderRepository.removeOrder(orderId);
                return;
            }
            Influencer influencer = influencerRepository.getInfluencerByName(order.getInfluencerId());
            influencer.removeCommission(findCommission(order.getPrice(), influencer));
            ongoingOrderRepository.removeOrder(orderId);
        } catch (Exception e) {
            log.error("Error occurred while returning order ", e);
        }
    }

    public synchronized List<Order> getListOfInfluencerOrder(String influencerId) {
        List<Order> orders = new ArrayList<>();
        try {
            orders = pastOrderRepository.getOrderForInfluencer(influencerId);
            return orders;
        } catch (Exception e) {
            log.error("Error occurred while fetching orders for influencer ", e);
        }
        return orders;
    }

}
