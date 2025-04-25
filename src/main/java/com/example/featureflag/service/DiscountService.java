package com.example.featureflag.service;

import com.example.featureflag.aspects.FeatureEnabled;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public double calculatePrice(double basePrice) {
        return basePrice * 0.9;
    }

    @FeatureEnabled("enable-discounts")
    public double applyDiscountIfEnabled(double basePrice) {
        return calculatePrice(basePrice);
    }
}
