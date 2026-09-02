package com.marcos.estik.domain.enums;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    SUPER("ROLE_SUPER"),
    USER("ROLE_USER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
