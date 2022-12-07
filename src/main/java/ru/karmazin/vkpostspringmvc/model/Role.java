package ru.karmazin.vkpostspringmvc.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Vladislav Karmazin
 */
public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
