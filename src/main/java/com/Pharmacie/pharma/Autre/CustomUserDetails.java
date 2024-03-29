package com.Pharmacie.pharma.Autre;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    
	private static final long serialVersionUID = 1L;
	private final String UserId;

    public CustomUserDetails(String UserId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.UserId = UserId;
    }

    public String getuserId() {
        return UserId;
    }
}
