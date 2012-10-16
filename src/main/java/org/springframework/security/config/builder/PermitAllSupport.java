package org.springframework.security.config.builder;

final class PermitAllSupport {

    public static void permitAll(SecurityFilterBuilder builder, String... urls) {
        DefaultFilters configurator = builder.getConfigurator(DefaultFilters.class);
        if(configurator != null) {
            FilterInvocationSecurityMetadataSourceBuilder fisBldr = configurator.filterInvocationSecurityMetadataSourceBuilder();
            for(String url : urls) {
                fisBldr.antInterceptUrl(url, "permitAll");
            }
        }
    }
}
