package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.util.RequestMatcher;

public class FilterChainProxyBuilder {
    private List<RequestMatcher> ignoredRequests = new ArrayList<RequestMatcher>();
    private List<SecurityFilterBuilder> filterChains = new ArrayList<SecurityFilterBuilder>();
    private WebInvocationPrivilegeEvaluator webSecurityExpressionHandler;

    public FilterChainProxyBuilder securityFilterChains(SecurityFilterBuilder... securityFilterChainBuilders) {
        filterChains = Arrays.asList(securityFilterChainBuilders);
        return this;
    }

    public FilterChainProxy build() throws Exception {
        int chainSize = ignoredRequests.size() + filterChains.size();
        List<SecurityFilterChain> securityFilterChains = new ArrayList<SecurityFilterChain>(chainSize);
        for(RequestMatcher ignoredRequest : ignoredRequests) {
            securityFilterChains.add(new DefaultSecurityFilterChain(ignoredRequest));
        }
        for(SecurityFilterBuilder builder : filterChains) {
            securityFilterChains.add(builder.build());
            webSecurityExpressionHandler = builder.webSecurityExpressionHandler();
        }
        return new FilterChainProxy(securityFilterChains);
    }

    public WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator() {
        return webSecurityExpressionHandler;
    }

    public FilterChainProxyBuilder ignoring(List<? extends RequestMatcher> requestsToIgnore) {
        this.ignoredRequests = new ArrayList<RequestMatcher>(requestsToIgnore);
        return this;
    }

}
