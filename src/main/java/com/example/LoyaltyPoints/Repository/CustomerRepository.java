package com.example.LoyaltyPoints.Repository;

import com.example.LoyaltyPoints.Entity.Customer;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
