package com.example.LoyaltyPoints.Service;

import com.example.LoyaltyPoints.Enum.AccountLevels;
import org.springframework.stereotype.Service;

@Service
public class PointsCalculatorService {

    public int CalculatePoints(AccountLevels accountLevel, double amount, int currentPointsBalance)
    {
        double accountRate = accountLevel.getPointsRate();

        int basePoints = (int) Math.floor(amount*accountRate);

        int bonusPoints = (int) Math.floor(basePoints * getBonusPoints(currentPointsBalance));

        return (basePoints + bonusPoints);
    }

    private double getBonusPoints(int balance)
    {
        if(balance >= 1000)
            return (0.50);
        else if(balance >= 500)
            return (0.20);
        else if(balance >= 100)
            return (0.10);
        else
            return(0.0);
    }
}
