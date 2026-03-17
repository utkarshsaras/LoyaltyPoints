package com.example.LoyaltyPoints.Repository;

import com.example.LoyaltyPoints.Entity.LoyaltyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyRepository extends JpaRepository<LoyaltyEvent, Long> {

    List<LoyaltyEvent> findByCustomerIdOrderByDateTime(String custId);
}
