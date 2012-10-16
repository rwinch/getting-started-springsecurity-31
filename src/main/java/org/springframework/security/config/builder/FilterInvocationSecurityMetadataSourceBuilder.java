package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

public class FilterInvocationSecurityMetadataSourceBuilder {
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();
    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher,Collection<ConfigAttribute>>();

    public FilterInvocationSecurityMetadataSourceBuilder antInterceptUrl(String pattern, String... configAttributes) {
        return interceptUrl(antMatchers(pattern), SecurityConfig.createList(configAttributes));
    }

    public FilterInvocationSecurityMetadataSourceBuilder antInterceptUrl(String pattern, Iterable<? extends ConfigAttribute> configAttributes) {
        return interceptUrl(antMatchers(pattern), configAttributes);
    }

    public FilterInvocationSecurityMetadataSourceBuilder interceptUrl(Iterable<? extends RequestMatcher> requestMatchers, String... configAttributes) {
        return interceptUrl(requestMatchers, SecurityConfig.createList(configAttributes));
    }

    public FilterInvocationSecurityMetadataSourceBuilder interceptUrl(Iterable<? extends RequestMatcher> requestMatchers, Iterable<? extends ConfigAttribute> configAttributes) {
        List<ConfigAttribute> configAttrs = new ArrayList<ConfigAttribute>();
        for(ConfigAttribute attr : configAttributes) {
            configAttrs.add(attr);
        }
        for(RequestMatcher matcher : requestMatchers) {
            requestMap.put(matcher,configAttrs);
        }
        return this;
    }

    public FilterInvocationSecurityMetadataSource build() {
        return new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap, expressionHandler);
    }

    public static List<AntPathRequestMatcher> antMatchers(String...antPatterns) {
        List<AntPathRequestMatcher> matchers = new ArrayList<AntPathRequestMatcher>();
        for(String pattern : antPatterns) {
            matchers.add(new AntPathRequestMatcher(pattern));
        }
        return matchers;
    }
}
