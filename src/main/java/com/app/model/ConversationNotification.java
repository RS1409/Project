package com.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="notifications")
public class ConversationNotification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    @ManyToOne
    @JoinColumn(name="conversation")
    private Conversation conversation;

    private String fromUser;
    private Long userFromId;


    public ConversationNotification() { }

    public ConversationNotification(User user, Conversation conversation, String fromUser, Long userFromId) {
        this.user = user;
        this.conversation = conversation;
        this.fromUser = fromUser;
        this.userFromId = userFromId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationNotification that = (ConversationNotification) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(conversation, that.conversation);
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Long getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Long userFromId) {
        this.userFromId = userFromId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, conversation);
    }

    @Override
    public String toString() {
        return "ConversationNotification{" +
                "user=" + user.getUsername() +
                ", conversation=" + conversation.getId() +
                '}';
    }


}
