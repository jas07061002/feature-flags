package com.example.featureflag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "feature_flags")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean enabled;

    @JsonIgnore
    private String environments;

    @Transient
    public List<String> getEnvironmentList() {
        return environments != null ? List.of(environments.split(",")) : List.of();
    }

    @Transient
    public void setEnvironmentList(List<String> envs) {
        this.environments = String.join(",", envs);
    }


}

