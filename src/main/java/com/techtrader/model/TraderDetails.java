package com.techtrader.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class TraderDetails implements UserDetails {
    private final Trader trader;
    public TraderDetails(Trader trader) {
        this.trader = trader;
    }
    @Override
    public String getUsername() {
        return trader.getUsername();
    }
    @Override
    public String getPassword() {
        return trader.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return   trader
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
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
        return true;
    }
}
