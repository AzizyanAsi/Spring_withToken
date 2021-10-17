package com.example.webik.security;

import com.example.webik.models.Authorities;
import com.example.webik.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private User user;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.user = user;
    }

    public AppUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user == null ? authorities
                :
                user.getAuthorities()
                        .stream()
                        .map(authorities -> new SimpleGrantedAuthority(authorities.getAuthority()))
                        .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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
        return user.isEnabled();
    }
}
