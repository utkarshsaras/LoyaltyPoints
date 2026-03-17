package com.example.LoyaltyPoints.Service;

import com.example.LoyaltyPoints.Entity.Customer;
import com.example.LoyaltyPoints.Entity.LoyaltyEvent;
import com.example.LoyaltyPoints.Enum.EventType;
import com.example.LoyaltyPoints.Exception.CustomerNotFoundException;
import com.example.LoyaltyPoints.Repository.CustomerRepository;
import com.example.LoyaltyPoints.Repository.LoyaltyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;
    private final CustomerRepository customerRepository;
    private final PointsCalculatorService pointsCalculatorService;

    public LoyaltyService(LoyaltyRepository loyaltyRepository, CustomerRepository customerRepository, PointsCalculatorService pointsCalculatorService)
    {
        this.loyaltyRepository = loyaltyRepository;
        this.customerRepository = customerRepository;
        this.pointsCalculatorService = pointsCalculatorService;
    }

    public int getPointsBalance(String customerId)
    {
        List<LoyaltyEvent> events = loyaltyRepository.findByCustomerIdOrderByDateTime(customerId);

        int pointsBalance = 0;

        for(LoyaltyEvent event: events)
        {
            if(event.getEventType() == EventType.PURCHASE)
            {
                pointsBalance += event.getPoints();
            }
            else if(event.getEventType() == EventType.REDEEM)
            {
                pointsBalance -= event.getPoints();
            }
        }

        return pointsBalance;
    }

    public int recordPurchase(String customerId, double amount)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        int currentPointsBalance = getPointsBalance(customerId);

        int earnedPoints = pointsCalculatorService.CalculatePoints(customer.getAccountLevel(), amount, currentPointsBalance);

        LoyaltyEvent event = new LoyaltyEvent();
        event.setCustomerId(customerId);
        event.setEventType(EventType.PURCHASE);
        event.setAmount(amount);
        event.setPoints(earnedPoints);
        event.setDateTime(LocalDateTime.now());

        loyaltyRepository.save(event);

        return (currentPointsBalance + earnedPoints);
    }

    public int redeemPoints(String customerId, int points)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        int currentPointsBalance = getPointsBalance(customerId);

        if(points > currentPointsBalance)
            throw new RuntimeException("Insufficient Balance");

        LoyaltyEvent event = new LoyaltyEvent();
        event.setCustomerId(customerId);
        event.setPoints(points);
        event.setEventType(EventType.REDEEM);
        event.setDateTime(LocalDateTime.now());

        loyaltyRepository.save(event);

        return(currentPointsBalance - points);
    }

    public List<LoyaltyEvent> getTransaactionsHistory(String customerId)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        List<LoyaltyEvent> transactions = loyaltyRepository.findByCustomerIdOrderByDateTime(customerId);

        return(transactions);
    }

}
