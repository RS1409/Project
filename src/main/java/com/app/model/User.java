package com.app.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{user.username.empty}")
    @Size(min=2, max=30,message = "{user.username.size}")
    private String userName;

    @NotNull(message = "{user.password.empty}")
    @Size(min=5, max=15, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.firstname.empty}")
    @Size(min=2, max=30,message = "{user.firstname.size}")
    private String firstName;

    @NotNull(message = "{user.lastname.empty}")
    @Size(min=2, max=30,message = "{user.lastname.size}")
    private String lastName;

    @Email(message = "{user.email}")
    @NotNull(message = "{user.email}")
    private String email;

    @NotNull(message = "{user.age.empty}")
    @Min(value = 18, message = "{user.age.toYoung}")
    private Integer age;

    @Lob
    @Column(name = "profile_image")
    private byte[] img;

    public User(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}