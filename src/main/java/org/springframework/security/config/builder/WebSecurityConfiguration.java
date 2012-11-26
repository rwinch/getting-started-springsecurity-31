package org.springframework.security.config.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.util.Assert;

@Configuration
public class WebSecurityConfiguration {
    @Autowired(required = false)
    private FilterChainProxyBuilder filterChainProxyBuilder;


    @Bean
    public FilterChainProxy springSecurityFilterChain() throws Exception {
        return filterChainProxyBuilder().build();
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }

    @Bean
    public WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator() throws Exception {
        springSecurityFilterChain();
        return filterChainProxyBuilder().webInvocationPrivilegeEvaluator();
    }

    private FilterChainProxyBuilder filterChainProxyBuilder() {
        Assert.notNull(filterChainProxyBuilder, "FilterChainProxyBuilder is required");
        return filterChainProxyBuilder;
    }
}
