package com.example.featureflag.service;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureFlagService {

    @Value("${spring.profiles.active:ALL}")
    private String activeEnv;

    private final FeatureFlagRepository repo;

    public FeatureFlagService(FeatureFlagRepository repo) {
        this.repo = repo;
    }

    public boolean isEnabled(String flagName) {
        return repo.findByNameAndEnvironment(flagName, activeEnv)
                .or(() -> repo.findByNameAndEnvironment(flagName, "ALL"))
                .map(FeatureFlag::getEnabled)
                .orElse(false);
    }

    public List<FeatureFlag> listFlags() {
        return repo.findByEnvironment(activeEnv);
    }

    public void updateFlag(String flagName, boolean enabled) {
        repo.findByNameAndEnvironment(flagName, activeEnv)
                .ifPresent(flag -> {
                    flag.setEnabled(enabled);
                    repo.save(flag);
                });
    }

    public void addFlag(FeatureFlag flag) {
        Optional<FeatureFlag> existing = repo.findByNameAndEnvironment(flag.getName(), flag.getEnvironment());
        if (existing.isEmpty()) {
            repo.save(flag);
        } else {
            throw new IllegalArgumentException("Feature flag already exists for this environment.");
        }
    }

}

