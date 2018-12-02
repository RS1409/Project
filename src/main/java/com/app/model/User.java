package com.app.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotNull(message = "{user.username.empty}")
    @Size(min = 2, max = 30, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.password.empty}")
    @Size(min = 5, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.firstname.empty}")
    @Size(min = 2, max = 30, message = "{user.firstname.size}")
    private String firstName;

    @NotNull(message = "{user.lastname.empty}")
    @Size(min = 2, max = 30, message = "{user.lastname.size}")
    private String lastName;

    @Email(message = "{user.email}")
    @NotEmpty(message = "{user.email}")
    private String email;

    @NotNull(message = "{user.age.empty}")
    @Min(value = 18, message = "{user.age.toYoung}")
    private Integer age;

    @Lob
    @Column(name = "profile_image")
    private byte[] img;

    @OneToOne(mappedBy = "user")
    private Preference preference;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    private boolean active;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("id DESC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<ConversationNotification> notifications;

    @Transient
    private String lastSearchRequest;

    public User() {
        this.posts = new LinkedList<>();
        this.notifications = new LinkedHashSet<>();
    }

    public void addPostToUser(Post post) {
        post.setUser(this);
        this.getPosts().add(post);
    }
    public void addPreference(Preference preference) {
        preference.setUser(this);
        this.setPreference(preference);
    }

    public void addConversationNotification(ConversationNotification conversationNotification)
    {
        this.notifications.add(conversationNotification);
        conversationNotification.setUser(this);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(LinkedList<Post> posts) {
        this.posts = posts;
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

    public Set<ConversationNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<ConversationNotification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, password, firstName, lastName, email, age);
    }
}