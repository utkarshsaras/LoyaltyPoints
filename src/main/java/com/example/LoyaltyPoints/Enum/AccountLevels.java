package com.example.LoyaltyPoints.Enum;

public enum AccountLevels {
    Bronze(0.02),
    Silver(0.05),
    Gold(0.07);

    private final double pointsRate;

    AccountLevels(double pointsRate)
    {
        this.pointsRate = pointsRate;
    }

    public double getPointsRate()
    {
        return pointsRate;
    }
}
