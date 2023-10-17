package com.dbms.project.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
public class User implements UserDetails {
    @NotBlank(message="Username cannot be blank")
    private Integer username = null;
    @NotEmpty(message="Password cannot be empty")
    @Size(min = 8, max=255, message="Password must have at least 8 characters")
    private String password = null;
    @NotBlank(message="Designation cannot be blank")
    private String designation = "Student";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String designation = getDesignation();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (Objects.equals(designation, "Employee")) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
        } else if (Objects.equals(designation, "Manager")) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            authorities.add(new SimpleGrantedAuthority("MANAGER"));
        } else if (Objects.equals(designation, "Admin")) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            authorities.add(new SimpleGrantedAuthority("MANAGER"));
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return authorities;
    }


    public boolean hasRole(String role) {
        return getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername(){
        return this.username.toString();
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
