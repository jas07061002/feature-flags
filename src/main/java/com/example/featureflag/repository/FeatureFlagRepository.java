package com.example.featureflag.repository;

import com.example.featureflag.model.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {
    Optional<FeatureFlag> findByNameAndEnvironment(String name, String environment);
    List<FeatureFlag> findByEnvironment(String environment);
}

