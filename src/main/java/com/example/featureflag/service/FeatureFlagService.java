package com.example.featureflag.service;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.repository.FeatureFlagRepository;
import com.example.featureflag.utils.EnvUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagService {

    private final FeatureFlagRepository repository;
    private final String activeEnv;

    public FeatureFlagService(FeatureFlagRepository repository, EnvUtils envUtils) {
        this.repository = repository;
        this.activeEnv = envUtils.getActiveProfile();
    }

    public boolean isEnabled(String flagName) {
        return repository.findByName(flagName).stream()
                .filter(flag -> flag.getEnvironmentList().contains(activeEnv))
                .findFirst()
                .map(FeatureFlag::getEnabled)
                .orElse(false);
    }

    public List<FeatureFlag> listFlags() {
        return repository.findByEnvironmentsContaining(activeEnv);
    }

    public void addFlag(FeatureFlag flag) {
        boolean exists = repository.findByName(flag.getName()).stream()
                .anyMatch(f -> f.getEnvironmentList().stream().anyMatch(env -> flag.getEnvironmentList().contains(env)));
        if (!exists) {
            repository.save(flag);
        } else {
            throw new IllegalArgumentException("Feature flag already exists for one or more specified environments.");
        }
    }

    public void updateFlag(String name, boolean enabled) {
        repository.findByName(name).stream()
                .filter(flag -> flag.getEnvironmentList().contains(activeEnv))
                .findFirst()
                .ifPresent(flag -> {
                    flag.setEnabled(enabled);
                    repository.save(flag);
                });
    }
}