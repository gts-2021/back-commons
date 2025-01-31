package com.gts.backcommons.jwtauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> publicPatterns = new ArrayList<>();

    public List<String> getPublicPatterns() {
        return publicPatterns;
    }

    public void setPublicPatterns(List<String> publicPatterns) {
        this.publicPatterns = publicPatterns;
    }
}
