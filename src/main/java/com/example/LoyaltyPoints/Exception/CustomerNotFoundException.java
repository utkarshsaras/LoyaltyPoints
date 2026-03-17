package com.example.LoyaltyPoints.Exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id)
    {
        super("Customer not found: " + id);
    }
}
