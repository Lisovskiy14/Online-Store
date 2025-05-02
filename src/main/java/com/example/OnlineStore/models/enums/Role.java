package com.example.OnlineStore.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
