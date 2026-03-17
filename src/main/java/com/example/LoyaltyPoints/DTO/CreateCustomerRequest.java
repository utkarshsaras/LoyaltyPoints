package com.example.LoyaltyPoints.DTO;

import com.example.LoyaltyPoints.Enum.AccountLevels;

public class CreateCustomerRequest {

    private String id;
    private AccountLevels accountLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountLevels getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(AccountLevels accountLevel) {
        this.accountLevel = accountLevel;
    }
}
