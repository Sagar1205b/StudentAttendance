package com.sahana.StudentAttendance.Security;

import com.sahana.StudentAttendance.Model.LicenseHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final LicenseHolder licenseHolder;

    public CustomUserDetails(LicenseHolder licenseHolder) {
        this.licenseHolder = licenseHolder;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(licenseHolder.getRole()));
    }

    @Override
    public String getPassword() {
        return licenseHolder.getPassword();
    }

    @Override
    public String getUsername() {
        return licenseHolder.getUsername();
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

