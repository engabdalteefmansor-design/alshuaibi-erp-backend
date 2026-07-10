package com.alshuaibi.erp.common.security;

import com.alshuaibi.erp.identity.permission.Permission;
import com.alshuaibi.erp.identity.role.Role;
import com.alshuaibi.erp.identity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final User user;
    private final List<GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.user = user;
        this.authorities = buildAuthorities(user);
    }

    private List<GrantedAuthority> buildAuthorities(User user) {
        List<GrantedAuthority> result = new ArrayList<>();

        Role role = user.getRole();
        if (role == null) {
            return result;
        }

        if (role.getCode() != null) {
            result.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().name()));
        }

        if (role.getPermissions() != null) {
            for (Permission permission : role.getPermissions()) {
                if (permission != null && permission.getCode() != null) {
                    result.add(new SimpleGrantedAuthority(permission.getCode().name()));
                }
            }
        }

        return result;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getActive() != null && user.getActive();
    }
}
