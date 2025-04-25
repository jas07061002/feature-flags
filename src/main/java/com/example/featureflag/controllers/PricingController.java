package com.example.featureflag.controllers;

import com.example.featureflag.service.PricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/final")
    public ResponseEntity<Double> getFinalPrice(@RequestParam double basePrice) {
        return ResponseEntity.ok(pricingService.getFinalPrice(basePrice));
    }
}