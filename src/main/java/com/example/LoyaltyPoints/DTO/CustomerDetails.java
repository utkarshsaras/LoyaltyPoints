package com.example.LoyaltyPoints.DTO;

import com.example.LoyaltyPoints.Enum.AccountLevels;

public class CustomerDetails {

    private String customerId;
    private String accountLevel;
    private int pointsBalance;

    public CustomerDetails(String customerId, String accountLevel, int pointsBalance)
    {
        this.customerId = customerId;
        this.accountLevel = accountLevel;
        this.pointsBalance = pointsBalance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public int getPointsBalance() {
        return pointsBalance;
    }
}
