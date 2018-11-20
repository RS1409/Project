package com.app.dto;

import com.app.model.Post;
import com.app.model.Roles;
import com.app.model.User;

import java.util.*;

public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private byte[] img;
    private Set<Roles> roles = new HashSet<>();
    private boolean active;
    private List<Post> posts = new LinkedList<>();
    private String lastSearchRequest;

    public UserDTO(User user)
    {
        this.id=user.getId();
        this.username=user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email=user.getEmail();
        this.age=user.getAge();
        this.img=user.getImg();
        this.roles.addAll(user.getRoles());
        this.posts.addAll(user.getPosts());
        this.active=user.isActive();
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getLastSearchRequest() {
        return lastSearchRequest;
    }

    public void setLastSearchRequest(String lastSearchRequest) {
        this.lastSearchRequest = lastSearchRequest;
    }

    public boolean isAdmin() {return roles.contains(Roles.ADMIN);}
}
