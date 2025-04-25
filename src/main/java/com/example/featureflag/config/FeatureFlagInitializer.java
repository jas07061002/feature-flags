package com.example.featureflag.config;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.service.FeatureFlagService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeatureFlagInitializer {

    private final FeatureFlagService service;

    public FeatureFlagInitializer(FeatureFlagService service) {
        this.service = service;
    }

    @PostConstruct
    public void initFlags() {
        List<FeatureFlag> initialFlags = List.of(
                new FeatureFlag(null, "enable-stripe", false, "qa,int,stage,local"),
                new FeatureFlag(null, "enable-fpt", true, "local"),
                new FeatureFlag(null, "enable-discounts", true, "qa,int,stage,local")
        );

        for (FeatureFlag flag : initialFlags) {
            try {
                service.addFlag(flag);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}
