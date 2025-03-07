package com.airmont.dto;

import com.airmont.models.entity.AdminUser;

public class UserResponse {
    private Long id;
    private String username;
    private String email;

    // Constructor
    public UserResponse(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.username = adminUser.getUsername();
        this.email = adminUser.getEmail();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
