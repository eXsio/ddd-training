package com.ddd.poc.domain.security.dto;

import com.google.common.collect.Lists;

import java.util.Collection;

public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private boolean active;

    private Collection<String> groups = Lists.newArrayList();



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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public void setGroups(Collection<String> groups) {
        this.groups = groups;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
