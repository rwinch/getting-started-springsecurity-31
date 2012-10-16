package org.springframework.security.config.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserBuilder {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder roles(String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);
        for(String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        this.authorities = authorities;
        return this;
    }

    public UserBuilder authorities(String... authorities) {
        this.authorities = AuthorityUtils.createAuthorityList(authorities);
        return this;
    }

    public UserDetails build() {
        return new User(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
    }

    public static UserBuilder user(String username) {
        UserBuilder result = new UserBuilder();
        result.username(username);
        return result;
    }
}