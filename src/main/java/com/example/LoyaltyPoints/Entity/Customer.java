package com.example.LoyaltyPoints.Entity;

import com.example.LoyaltyPoints.Enum.AccountLevels;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
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
