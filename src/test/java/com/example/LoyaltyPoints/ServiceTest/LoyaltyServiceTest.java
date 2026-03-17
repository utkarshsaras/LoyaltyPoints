package com.example.LoyaltyPoints.ServiceTest;

import com.example.LoyaltyPoints.Entity.Customer;
import com.example.LoyaltyPoints.Entity.LoyaltyEvent;
import com.example.LoyaltyPoints.Enum.AccountLevels;
import com.example.LoyaltyPoints.Enum.EventType;
import com.example.LoyaltyPoints.Repository.CustomerRepository;
import com.example.LoyaltyPoints.Repository.LoyaltyRepository;
import com.example.LoyaltyPoints.Service.LoyaltyService;
import com.example.LoyaltyPoints.Service.PointsCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoyaltyServiceTest {

    @Mock
    private LoyaltyRepository loyaltyRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PointsCalculatorService pointsCalculatorService;

    @InjectMocks
    private LoyaltyService loyaltyService;

    @Test
    void shouldCalculateBalanceFromEvents() {

        String customerId = "cust-123";

        LoyaltyEvent purchase1 = new LoyaltyEvent();
        purchase1.setCustomerId(customerId);
        purchase1.setEventType(EventType.PURCHASE);
        purchase1.setPoints(10);
        purchase1.setDateTime(LocalDateTime.now());

        LoyaltyEvent purchase2 = new LoyaltyEvent();
        purchase2.setCustomerId(customerId);
        purchase2.setEventType(EventType.PURCHASE);
        purchase2.setPoints(5);
        purchase2.setDateTime(LocalDateTime.now());

        LoyaltyEvent redeem = new LoyaltyEvent();
        redeem.setCustomerId(customerId);
        redeem.setEventType(EventType.REDEEM);
        redeem.setPoints(3);
        redeem.setDateTime(LocalDateTime.now());

        when(loyaltyRepository.findByCustomerIdOrderByDateTime(customerId))
                .thenReturn(List.of(purchase1, purchase2, redeem));

        int balance = loyaltyService.getPointsBalance(customerId);

        assertEquals(12, balance);
    }

    @Test
    void shouldRecordPurchaseAndReturnNewBalance() {

        String customerId = "cust-123";
        double purchaseAmount = 100;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setAccountLevel(AccountLevels.Silver);

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(loyaltyRepository.findByCustomerIdOrderByDateTime(customerId))
                .thenReturn(List.of());

        when(pointsCalculatorService.CalculatePoints(AccountLevels.Silver, purchaseAmount, 0))
                .thenReturn(5);

        int result = loyaltyService.recordPurchase(customerId, purchaseAmount);

        assertEquals(5, result);
    }
}
