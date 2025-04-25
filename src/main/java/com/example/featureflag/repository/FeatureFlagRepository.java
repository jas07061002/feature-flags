package com.example.featureflag.repository;

import com.example.featureflag.model.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {
    List<FeatureFlag> findByName(String name);

    List<FeatureFlag> findByEnvironmentsContaining(String environment);

    List<FeatureFlag> findAll();
}