package com.example.LoyaltyPoints.Service;

import com.example.LoyaltyPoints.Entity.Customer;
import com.example.LoyaltyPoints.Enum.AccountLevels;
import com.example.LoyaltyPoints.Exception.CustomerNotFoundException;
import com.example.LoyaltyPoints.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerDetails(String id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return customer;
    }

    public Customer createCustomer(String customerId, AccountLevels accountLevel)
    {
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setAccountLevel(accountLevel);

        Customer customer = customerRepository.save(newCustomer);

        return customer;
    }
}
