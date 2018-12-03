package com.app.model;


import javax.persistence.*;
import java.io.Serializable;

import java.util.Objects;


@Entity
@Table(name="friend_requests")
public class FriendRequest implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_friend_request")
    private Long id;
    private String message;
    private Status status = Status.REQUESTED;


    @ManyToOne
    @JoinColumn(name="fromUser")
    private User from;

    @ManyToOne
    @JoinColumn(name="toUser")
    private User to;


    public FriendRequest() {
        this.message="hello";
        System.out.println("Creating new Friend Request");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {

        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", from='" + from.getId() + '\'' +
                '}';
    }

    public enum Status {
        ACCEPTED, REQUESTED
    }
}
