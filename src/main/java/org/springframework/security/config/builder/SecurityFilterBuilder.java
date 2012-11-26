package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.util.AnyRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

public class SecurityFilterBuilder {
    private AuthenticationManager authenticationManager;
    private List<Filter> filters =  new ArrayList<Filter>();
    private RequestMatcher requestMatcher = new AnyRequestMatcher();
    private Comparator<Filter> comparitor = new FilterComparitor();
    private final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<RequestMatcher, AuthenticationEntryPoint>();
    private final DelegatingAuthenticationEntryPoint authenticationEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
    private final List<SecurityFilterConfigurator> configurators = new ArrayList<SecurityFilterConfigurator>();

    public SecurityFilterBuilder(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public DefaultSecurityFilterChain build() throws Exception {
        for(SecurityFilterConfigurator configurer : configurators) {
            configurer.init(this);
        }
        for(SecurityFilterConfigurator configurer : configurators) {
            configurer.configure(this);
        }
        Collections.sort(filters,comparitor);
        return new DefaultSecurityFilterChain(requestMatcher, filters);
    }

    public SecurityFilterBuilder apply(SecurityFilterConfigurator configurer) throws Exception {
        this.configurators.add(configurer);
        return this;
    }

    @SuppressWarnings("unchecked")
	public <T extends SecurityFilterConfigurator> T getConfigurator(Class<T> clazz) {
        for(SecurityFilterConfigurator configurer : configurators) {
            if(configurer.getClass().isAssignableFrom(clazz)) {
                return (T) configurer;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
	public <T extends Filter> T getFilter(Class<T> clazz) {
        for(Filter f : filters) {
            if(f.getClass().isAssignableFrom(clazz)) {
                return (T) f;
            }
        }
        return null;
    }

    public WebInvocationPrivilegeEvaluator webSecurityExpressionHandler() {
        FilterSecurityInterceptor fsi = getFilter(FilterSecurityInterceptor.class);
        return new DefaultWebInvocationPrivilegeEvaluator(fsi);
    }

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public AuthenticationManager authenticationManager() {
        return authenticationManager;
    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public SecurityFilterBuilder authenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint.setDefaultEntryPoint(authenticationEntryPoint);
        return this;
    }
}