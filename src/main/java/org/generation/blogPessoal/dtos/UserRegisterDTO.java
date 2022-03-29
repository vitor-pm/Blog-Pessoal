package org.generation.blogPessoal.dtos;

import javax.validation.constraints.NotNull;

public class UserRegisterDTO {

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String picture;

    private String role;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserRegisterDTO(@NotNull String name, @NotNull String username, @NotNull String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
