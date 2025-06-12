package com.example.hotelManagementBackend.Auth;

import com.example.hotelManagementBackend.entities.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class GuestDetails implements UserDetails {

//    @Autowired
    private Guest guest;

    public GuestDetails(Guest guest) {
        this.guest = guest;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(guest.getRole()));
    }

    @Override
    public String getPassword() {
        return guest.getPassword();
    }

    @Override
    public String getUsername() {
        return guest.getEmail();
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
