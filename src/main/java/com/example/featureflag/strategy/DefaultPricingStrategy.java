package com.example.featureflag.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}