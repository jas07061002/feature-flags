package com.example.featureflag.strategy;

import org.springframework.stereotype.Component;

@Component
public class DiscountedPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.9;
    }
}