package org.springframework.security.config.builder;

public class SecurityExpressions {
    public static final String permitAll = "permitAll";

    public static String hasRole(String role) {
        return "hasRole('ROLE_"+role+"')";
    }
}
