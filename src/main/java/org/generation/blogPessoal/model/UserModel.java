package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tb_user")
public class UserModel {

    // System generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Schema(example = "email@email.com.br")
    @NotNull
    @Size(min = 5, max = 100)
    private String username;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @Size(max = 5000)
    private String picture;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("creator")
    List<Post> myPosts;

    public List<Post> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<Post> myPosts) {
        this.myPosts = myPosts;
    }

    // Constructors
    public UserModel() {
    }

    public UserModel(long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel(@NotNull @Size(min = 2, max = 100) String name, @NotNull @Size(min = 5, max = 100) String username,
            @NotNull @Size(min = 5, max = 100) String password, String picture, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
