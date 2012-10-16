package org.springframework.security.config.builder;

import java.util.ArrayList;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


public class InMemoryUserDetailsBuilder {
    private InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(new ArrayList<UserDetails>());

    public DaoAuthenticationProvider build() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        return provider;
    }

    public InMemoryUserDetailsBuilder addUsers(UserBuilder... userBuilders) {
        for(UserBuilder userBuilder : userBuilders) {
            userDetailsManager.createUser(userBuilder.build());
        }
        return this;
    }
}
