package com.example.featureflag.controllers;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.service.FeatureFlagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature-flags")
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;

    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @GetMapping
    public List<FeatureFlag> getAll() {
        return featureFlagService.listFlags();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Boolean> getStatus(@PathVariable String name) {
        return ResponseEntity.ok(featureFlagService.isEnabled(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestParam boolean enabled) {
        featureFlagService.updateFlag(name, enabled);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFlag(@RequestBody FeatureFlag flag) {
        featureFlagService.addFlag(flag);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

