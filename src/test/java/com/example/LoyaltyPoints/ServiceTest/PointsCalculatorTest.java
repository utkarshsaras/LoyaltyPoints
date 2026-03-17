package com.example.LoyaltyPoints.ServiceTest;

import com.example.LoyaltyPoints.Enum.AccountLevels;
import com.example.LoyaltyPoints.Service.PointsCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointsCalculatorTest {

    PointsCalculatorService pointsCalculatorService = new PointsCalculatorService();

    @Test
    void silverExplorerPurchase()
    {
        int points = pointsCalculatorService.CalculatePoints(
                AccountLevels.Silver,
                100,
                150
        );

        assertEquals(5, points);
    }
}
