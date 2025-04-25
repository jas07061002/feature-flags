package com.example.featureflag.service;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.repository.FeatureFlagRepository;
import com.example.featureflag.utils.EnvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeatureFlagServiceTest {

    @Mock
    private FeatureFlagRepository repository;

    @Mock
    private EnvUtils envUtils;

    @InjectMocks
    private FeatureFlagService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(envUtils.getActiveProfile()).thenReturn("qa");
        service = new FeatureFlagService(repository, envUtils);
    }

    @Test
    void testAddNewFlagSuccessfully() {
        FeatureFlag flag = new FeatureFlag(null, "feature-a", true, "qa,int");
        when(repository.findByName(flag.getName())).thenReturn(List.of());
        service.addFlag(flag);
        verify(repository, times(1)).save(flag);
    }

    @Test
    void testAddDuplicateFlagThrowsException() {
        FeatureFlag flag = new FeatureFlag(null, "feature-b", true, "qa");
        when(repository.findByName(flag.getName())).thenReturn(List.of(flag));
        assertThrows(IllegalArgumentException.class, () -> service.addFlag(flag));
    }

    @Test
    void testIsEnabledTrueForMatchingEnv() {
        FeatureFlag flag = new FeatureFlag(1L, "feature-c", true, "qa,stage");
        when(repository.findByName("feature-c")).thenReturn(List.of(flag));
        assertTrue(service.isEnabled("feature-c"));
    }

    @Test
    void testIsEnabledFalseWhenNotFound() {
        when(repository.findByName("unknown-feature")).thenReturn(List.of());
        assertFalse(service.isEnabled("unknown-feature"));
    }

    @Test
    void testUpdateFlagSuccessfully() {
        FeatureFlag flag = new FeatureFlag(1L, "feature-d", false, "qa,stage");
        when(repository.findByName("feature-d")).thenReturn(List.of(flag));
        service.updateFlag("feature-d", true);
        assertTrue(flag.getEnabled());
        verify(repository).save(flag);
    }

    @Test
    void testListFlagsReturnsOnlyMatchingEnv() {
        FeatureFlag flag1 = new FeatureFlag(1L, "feature-x", true, "qa");
        FeatureFlag flag2 = new FeatureFlag(2L, "feature-y", true, "qa,stage");
        when(repository.findByEnvironmentsContaining("qa")).thenReturn(List.of(flag1, flag2));
        List<FeatureFlag> result = service.listFlags();
        assertEquals(2, result.size());
    }
}

