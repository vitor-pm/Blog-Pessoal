package org.generation.blogPessoal.dtos;

public class UserCredentialsDTO {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String token;

    private String picture;

    private String role;

    public UserCredentialsDTO(String token, Long id, String name, String username, String password, String picture,
            String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.token = token;
        this.picture = picture;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

}
