package com.example.featureflag.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvUtils {

    private final Environment environment;

    public EnvUtils(Environment env) {
        this.environment = env;
    }

    public String getActiveProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        return (activeProfiles.length > 0) ? activeProfiles[0] : "ALL";
    }
}
