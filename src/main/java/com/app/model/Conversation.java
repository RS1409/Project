package com.app.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="conversations")
public class Conversation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="firstUser")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name="secondUser")
    private User secondUser;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("id ASC")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "conversation", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<ConversationNotification> notifications = new LinkedHashSet<>();

    private String status;
    private Long lastAuthorId;

    public Conversation(){}

    public void addMessage(Message message)
    {
        message.setConversation(this);
        this.getMessages().add(message);
    }

    public void addConversationNotification(ConversationNotification conversationNotification)
    {
        this.notifications.add(conversationNotification);
        conversationNotification.setConversation(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLastAuthorId() {
        return lastAuthorId;
    }

    public void setLastAuthorId(Long lastAuthorId) {
        this.lastAuthorId = lastAuthorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(firstUser, that.firstUser) &&
                Objects.equals(secondUser, that.secondUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstUser, secondUser);
    }
}
