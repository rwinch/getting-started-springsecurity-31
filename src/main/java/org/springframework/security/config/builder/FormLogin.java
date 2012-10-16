package org.springframework.security.config.builder;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FormLogin implements SecurityFilterConfigurator {
    private UsernamePasswordAuthenticationFilter usernamePasswordFilter = new UsernamePasswordAuthenticationFilter();
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler;
    private boolean permitAll;
    private String loginUrl = "/j_spring_security_check";
    private String failureUrl;
    private String loginFormUrl = "/login";
    private AuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(loginFormUrl);

    public FormLogin() {
        failureUrl("/login?error");
    }

    public void init(SecurityFilterBuilder builder) throws Exception {
        if(permitAll) {
            PermitAllSupport.permitAll(builder, loginUrl, failureUrl, loginFormUrl);
        }
    }

    public void configure(SecurityFilterBuilder builder) throws Exception {
        usernamePasswordFilter.setAuthenticationManager(builder.authenticationManager());
        usernamePasswordFilter.setAuthenticationSuccessHandler(successHandler);
        usernamePasswordFilter.setAuthenticationFailureHandler(failureHandler);
        usernamePasswordFilter.afterPropertiesSet();
        builder.authenticationEntryPoint(authenticationEntryPoint);
        builder.addFilter(usernamePasswordFilter);
    }

    public FormLogin loginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        usernamePasswordFilter.setFilterProcessesUrl(loginUrl);
        return this;
    }

    public FormLogin usernameParameter(String usernameParameter) {
        usernamePasswordFilter.setUsernameParameter(usernameParameter);
        return this;
    }

    public FormLogin passwordParameter(String passwordParameter) {
        usernamePasswordFilter.setPasswordParameter(passwordParameter);
        return this;
    }

    public FormLogin permitAll() {
        return permitAll(true);
    }

    public FormLogin permitAll(boolean permitAll) {
        this.permitAll = permitAll;
        return this;
    }

    public FormLogin failureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
        this.failureHandler = new SimpleUrlAuthenticationFailureHandler(failureUrl);
        return this;
    }
}
