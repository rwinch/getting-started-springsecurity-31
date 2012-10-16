package org.springframework.security.config.builder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.jaasapi.JaasApiIntegrationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 *
    CHANNEL_FILTER,
    CONCURRENT_SESSION_FILTER,
    SECURITY_CONTEXT_FILTER,
    LOGOUT_FILTER,
    X509_FILTER,
    PRE_AUTH_FILTER,
    CAS_FILTER,
    FORM_LOGIN_FILTER,
    OPENID_FILTER,
    LOGIN_PAGE_FILTER,
    DIGEST_AUTH_FILTER,
    BASIC_AUTH_FILTER,
    REQUEST_CACHE_FILTER,
    SERVLET_API_SUPPORT_FILTER,
    JAAS_API_SUPPORT_FILTER,
    REMEMBER_ME_FILTER,
    ANONYMOUS_FILTER,
    SESSION_MANAGEMENT_FILTER,
    EXCEPTION_TRANSLATION_FILTER,
    FILTER_SECURITY_INTERCEPTOR,
    SWITCH_USER_FILTER,
    LAST (Integer.MAX_VALUE);
 * @author rw012795
 *
 */
class FilterComparitor implements Comparator<Filter>{
    private static int STEP = 100;
    private Map<Class<? extends Filter>,Integer> filterToOrder = new HashMap<Class<? extends Filter>,Integer>();

    public FilterComparitor() {
        int order = 100;
        filterToOrder.put(ChannelProcessingFilter.class, order);
        order += STEP;
        filterToOrder.put(ConcurrentSessionFilter.class, order);
        order += STEP;
        filterToOrder.put(SecurityContextPersistenceFilter.class, order);
        order += STEP;
        filterToOrder.put(LogoutFilter.class, order);
        order += STEP;
        filterToOrder.put(X509AuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(AbstractPreAuthenticatedProcessingFilter.class, order);
        order += STEP;
//        filterToOrder.put(CasFilter, order);
//        order += STEP;
        filterToOrder.put(UsernamePasswordAuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(ConcurrentSessionFilter.class, order);
        order += STEP;
//        filterToOrder.put(OpenIDFilter.class, order);
//        order += STEP;
//        filterToOrder.put(LoginPage.class, order);
//        order += STEP;
        filterToOrder.put(ConcurrentSessionFilter.class, order);
        order += STEP;
        filterToOrder.put(DigestAuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(BasicAuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(RequestCacheAwareFilter.class, order);
        order += STEP;
        filterToOrder.put(SecurityContextHolderAwareRequestFilter.class, order);
        order += STEP;
        filterToOrder.put(JaasApiIntegrationFilter.class, order);
        order += STEP;
        filterToOrder.put(RememberMeAuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(AnonymousAuthenticationFilter.class, order);
        order += STEP;
        filterToOrder.put(SessionManagementFilter.class, order);
        order += STEP;
        filterToOrder.put(ExceptionTranslationFilter.class, order);
        order += STEP;
        filterToOrder.put(FilterSecurityInterceptor.class, order);
        order += STEP;
        filterToOrder.put(SwitchUserFilter.class, order);
    }

    public int compare(Filter lhs, Filter rhs) {
        Integer left = filterToOrder.get(lhs.getClass());
        Integer right = filterToOrder.get(rhs.getClass());
        return left - right;
    }

}
