package com.example.featureflag.service;

import com.example.featureflag.strategy.DefaultPricingStrategy;
import com.example.featureflag.strategy.DiscountedPricingStrategy;
import com.example.featureflag.strategy.PricingStrategy;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private final PricingStrategy strategy;

    public PricingService(FeatureFlagService featureFlagService,
                          DefaultPricingStrategy defaultStrategy,
                          DiscountedPricingStrategy discountedStrategy) {
        this.strategy = featureFlagService.isEnabled("enable-discounts") ? discountedStrategy : defaultStrategy;
    }

    public double getFinalPrice(double basePrice) {
        return strategy.calculatePrice(basePrice);
    }
}