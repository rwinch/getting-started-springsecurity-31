package org.springframework.security.samples.config;

import static org.springframework.security.config.builder.FilterInvocationSecurityMetadataSourceBuilder.antMatchers;
import static org.springframework.security.config.builder.SecurityExpressions.hasRole;
import static org.springframework.security.config.builder.SecurityExpressions.permitAll;
import static org.springframework.security.config.builder.UserBuilder.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.builder.AuthenticationManagerBuilder;
import org.springframework.security.config.builder.DefaultFilters;
import org.springframework.security.config.builder.EnableWebSecurity;
import org.springframework.security.config.builder.FilterChainProxyBuilder;
import org.springframework.security.config.builder.FilterInvocationSecurityMetadataSourceBuilder;
import org.springframework.security.config.builder.FormLogin;
import org.springframework.security.config.builder.InMemoryUserDetailsBuilder;
import org.springframework.security.config.builder.SecurityFilterBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManagerBuilder().userDetails(new InMemoryUserDetailsBuilder()
            .addUsers(user("user").password("password").roles("USER"),
                      user("admin").password("admin").roles("USER", "ADMIN"))
        ).build();
    }

    @Bean
    public FilterChainProxyBuilder builder() throws Exception {
        AuthenticationManager providerManager = authenticationManager();
        FilterInvocationSecurityMetadataSourceBuilder fiSourceBldr = new FilterInvocationSecurityMetadataSourceBuilder()
            .interceptUrl(antMatchers("/users**","/sessions/**"), hasRole("ADMIN"))
            .interceptUrl(antMatchers("/resources/**","/signup","/login","/logout"), permitAll)
            .antInterceptUrl("/**", hasRole("USER"));

        return new FilterChainProxyBuilder()
            .ignoring(antMatchers("/resources/**"))
            .securityFilterChains(
                new SecurityFilterBuilder(providerManager)
                    .apply(new DefaultFilters(fiSourceBldr))
                    .apply(new FormLogin()));
    }
}
