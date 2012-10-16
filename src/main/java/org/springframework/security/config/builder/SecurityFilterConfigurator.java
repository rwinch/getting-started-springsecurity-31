package org.springframework.security.config.builder;

public interface SecurityFilterConfigurator {

    void init(SecurityFilterBuilder builder) throws Exception;

    void configure(SecurityFilterBuilder builder) throws Exception;
}
