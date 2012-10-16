package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

public class DefaultFilters implements SecurityFilterConfigurator {
    private FilterInvocationSecurityMetadataSourceBuilder securityMetadataSource;
    private String logoutUrl = "/j_spring_security_logout";
    private boolean permitAll;

    public DefaultFilters(FilterInvocationSecurityMetadataSourceBuilder securityMetadataSource) {
        super();
        this.securityMetadataSource = securityMetadataSource;
    }

    public void init(SecurityFilterBuilder builder) throws Exception {
        if(permitAll) {
            PermitAllSupport.permitAll(builder, logoutUrl);
        }
    }

    public DefaultFilters permitAll() {
        return permitAll(true);
    }

    public DefaultFilters permitAll(boolean permitAll) {
        this.permitAll = permitAll;
        return this;
    }

    public FilterInvocationSecurityMetadataSourceBuilder filterInvocationSecurityMetadataSourceBuilder() {
        return securityMetadataSource;
    }

    public void configure(SecurityFilterBuilder builder) throws Exception {

        SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter();
        securityContextFilter.afterPropertiesSet();

        LogoutFilter logoutFilter = new LogoutFilter((String) null, new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(logoutUrl);
        logoutFilter.afterPropertiesSet();

        RequestCacheAwareFilter requestCacheFilter = new RequestCacheAwareFilter();

        SecurityContextHolderAwareRequestFilter securityContextRequestFilter = new SecurityContextHolderAwareRequestFilter();
        securityContextRequestFilter.afterPropertiesSet();

        AnonymousAuthenticationFilter anonymousAuthenticationFilter = new AnonymousAuthenticationFilter(
                "anonymousAuthenticationFilter");
        anonymousAuthenticationFilter.afterPropertiesSet();

        ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(builder.authenticationEntryPoint());

        builder.addFilter(securityContextFilter);
        builder.addFilter(logoutFilter);
        builder.addFilter(requestCacheFilter);
        builder.addFilter(securityContextRequestFilter);
        builder.addFilter(anonymousAuthenticationFilter);
        builder.addFilter(exceptionTranslationFilter);
        builder.addFilter(securityInterceptor(builder.authenticationManager()));
    }

    private FilterSecurityInterceptor securityInterceptor(AuthenticationManager authenticationManager) throws Exception {
        List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();
        decisionVoters.add(new WebExpressionVoter());
        ConsensusBased accessDecisionManager = new ConsensusBased(decisionVoters);
        FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
        securityInterceptor.setSecurityMetadataSource(securityMetadataSource.build());
        securityInterceptor.setAccessDecisionManager(accessDecisionManager);
        securityInterceptor.setAuthenticationManager(authenticationManager);
        securityInterceptor.afterPropertiesSet();
        return securityInterceptor;
    }
}
