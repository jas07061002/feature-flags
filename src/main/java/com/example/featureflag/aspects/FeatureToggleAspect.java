package com.example.featureflag.aspects;

import com.example.featureflag.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
// for small changes , methods or small refactors
public class FeatureToggleAspect {

    private final FeatureFlagService featureFlagService;

    @Around("@annotation(featureEnabled)")
    public Object handleFeatureToggle(ProceedingJoinPoint joinPoint, FeatureEnabled featureEnabled) throws Throwable {
        String flagName = featureEnabled.value();
        if (featureFlagService.isEnabled(flagName)) {
            return joinPoint.proceed();
        } else {
            return null;
        }
    }
}

