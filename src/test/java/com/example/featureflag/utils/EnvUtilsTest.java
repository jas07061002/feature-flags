package com.example.featureflag.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnvUtilsTest {

    @Test
    void testGetActiveProfileReturnsFirstActive() {
        Environment env = mock(Environment.class);
        when(env.getActiveProfiles()).thenReturn(new String[]{"dev", "qa"});

        EnvUtils envUtils = new EnvUtils(env);
        assertEquals("dev", envUtils.getActiveProfile());
    }

    @Test
    void testGetActiveProfileReturnsDefaultAll() {
        Environment env = mock(Environment.class);
        when(env.getActiveProfiles()).thenReturn(new String[]{});

        EnvUtils envUtils = new EnvUtils(env);
        assertEquals("ALL", envUtils.getActiveProfile());
    }
}
