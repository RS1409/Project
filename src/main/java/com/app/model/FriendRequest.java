package com.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="`friend_requests`")
public class FriendRequest implements Serializable{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="`id_friend_request`")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "`user_requsets`")
    private User user;

    private Long fromUser;
    private Long toUser;
    private String message;



    public FriendRequest() {
        System.out.println("Creating new Friend Request");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(fromUser, that.fromUser) &&
                Objects.equals(toUser, that.toUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fromUser, toUser);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", message='" + message + '\'' +
                '}';
    }
}
