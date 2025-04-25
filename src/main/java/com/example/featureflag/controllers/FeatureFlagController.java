package com.example.featureflag.controllers;

import com.example.featureflag.model.FeatureFlag;
import com.example.featureflag.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature-flags")
public class FeatureFlagController {

    private final FeatureFlagService service;

    public FeatureFlagController(FeatureFlagService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeatureFlag> getAll() {
        return service.listFlags();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Boolean> getStatus(@PathVariable String name) {
        return ResponseEntity.ok(service.isEnabled(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestParam boolean enabled) {
        service.updateFlag(name, enabled);
        return ResponseEntity.ok().build();
    }
}

