package com.example.featureflag.aspects;

import com.example.featureflag.service.FeatureFlagService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FeatureToggleAspectTest {

    private FeatureToggleAspect aspect;
    private FeatureFlagService flagService;
    private ProceedingJoinPoint joinPoint;
    private MethodSignature methodSignature;

    @BeforeEach
    void setUp() throws Exception {
        flagService = mock(FeatureFlagService.class);
        aspect = new FeatureToggleAspect(flagService);
        joinPoint = mock(ProceedingJoinPoint.class);
        methodSignature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);

        Method testMethod = TestTarget.class.getMethod("flaggedMethod");
        when(methodSignature.getMethod()).thenReturn(testMethod);
        when(methodSignature.getReturnType()).thenReturn(String.class);
    }

    @Test
    void proceedsWhenFeatureIsEnabled() throws Throwable {
        when(flagService.isEnabled("test-flag"))
                .thenReturn(true);
        when(joinPoint.proceed()).thenReturn("Executed");

        FeatureEnabled annotation = TestTarget.class.getMethod("flaggedMethod").getAnnotation(FeatureEnabled.class);
        Object result = aspect.handleFeatureToggle(joinPoint, annotation);
        assertEquals("Executed", result);
    }

    @Test
    void skipsWhenFeatureIsDisabled() throws Throwable {
        when(flagService.isEnabled("test-flag"))
                .thenReturn(false);

        FeatureEnabled annotation = TestTarget.class.getMethod("flaggedMethod").getAnnotation(FeatureEnabled.class);
        Object result = aspect.handleFeatureToggle(joinPoint, annotation);
        assertEquals(null, result);
    }

    interface TestTarget {
        @FeatureEnabled("test-flag")
        String flaggedMethod();
    }
}