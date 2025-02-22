package org.example;

import org.example.computationstrategy.CommissionStrategyFactory;
import org.example.respository.*;
import org.example.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        InfluencerRepository influencerRepository = new InfluencerRepository();
        ItemRepository itemRepository = new ItemRepository();
        LinkRepository linkRepository = new LinkRepository();
        OngoingOrderRepository ongoingOrderRepository = new OngoingOrderRepository();
        PastOrderRepository pastOrderRepository = new PastOrderRepository();
        InfluencerService influencerService = new InfluencerService(influencerRepository);
        OrderService orderService = new OrderService(linkRepository, influencerRepository, ongoingOrderRepository, pastOrderRepository, itemRepository);
        ItemService itemService = new ItemService(itemRepository);
        AffiliateService affiliateService = new AffiliateService(linkRepository);
        PaymentService paymentService = new PaymentService(pastOrderRepository, ongoingOrderRepository, influencerRepository);

        itemService.addItem("Wallet", 400);
        itemService.addItem("Phone", 40000);
        itemService.addItem("Watch", 4000);
        influencerService.addInfluencer("ABC", "PERCENTAGE", 10);
        influencerService.addInfluencer("XYZ", "FLAT", 100);
        affiliateService.addLink("url1", "Wallet", "ABC");
        affiliateService.addLink("url2", "Wallet", "ABC");

        orderService.placeOrder("url1");
        orderService.placeOrder("url2");

        System.out.println(itemService.getItem("Wallet"));
        System.out.println(influencerService.getInfluencer("ABC"));
        System.out.println(orderService.getListOfInfluencerOrder("ABC"));
        String orderId=orderService.placeOrder("url1");
        System.out.println(orderId);
        System.out.println("pending payment is "+influencerService.getPendingPaymentForInfluencer("ABC"));
        paymentService.makePayment(orderId);
        System.out.println(orderService.getListOfInfluencerOrder("ABC"));
        System.out.println(influencerService.getTotalEarningForInfluencer("ABC"));


        System.out.println("Hello world!");
//        SpringApplication.run(Main.class, args);
    }
}