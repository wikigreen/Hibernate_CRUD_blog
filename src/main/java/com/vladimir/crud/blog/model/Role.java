package com.vladimir.crud.blog.model;

import java.util.Arrays;

public enum Role {
    ADMIN("ADMIN"), MODERATOR("MODERATOR"), USER("USER");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String toString() {
        return role;
    }

    public static Role parseRole(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.toString().equals(role))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(role + " is not a role"));
    }
}
