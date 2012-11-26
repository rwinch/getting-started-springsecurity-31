package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

public class AuthenticationManagerBuilder {
    private List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();

    public AuthenticationManager build() throws Exception {
        ProviderManager providerManager = new ProviderManager(providers);
        providerManager.afterPropertiesSet();
        return providerManager;
    }

    public AuthenticationManagerBuilder userDetails(InMemoryUserDetailsBuilder userDetailsBldr) {
        providers.add(userDetailsBldr.build());
        return this;
    }

}
