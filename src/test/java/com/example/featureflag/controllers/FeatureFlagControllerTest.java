package com.example.featureflag.controllers;


import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.service.FeatureFlagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeatureFlagControllerTest {

    private FeatureFlagService featureFlagService;
    private FeatureFlagController controller;

    @BeforeEach
    void setUp() {
        featureFlagService = mock(FeatureFlagService.class);
        controller = new FeatureFlagController(featureFlagService);
    }

    @Test
    void testGetAll() {
        when(featureFlagService.listFlags()).thenReturn(List.of(new FeatureFlag(1L, "test-flag", true, "qa")));
        List<FeatureFlag> result = controller.getAll();
        assertEquals(1, result.size());
    }

    @Test
    void testGetStatus() {
        when(featureFlagService.isEnabled("some-flag")).thenReturn(true);
        ResponseEntity<Boolean> response = controller.getStatus("some-flag");
        assertEquals(true, response.getBody());
    }

    @Test
    void testAddFlag() {
        FeatureFlag flag = new FeatureFlag(null, "new-flag", true, "qa");
        ResponseEntity<Void> response = controller.addFlag(flag);
        verify(featureFlagService).addFlag(flag);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateFlag() {
        ResponseEntity<Void> response = controller.update("update-flag", true);
        verify(featureFlagService).updateFlag("update-flag", true);
        assertEquals(200, response.getStatusCodeValue());
    }
}