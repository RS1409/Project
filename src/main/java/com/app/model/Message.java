package com.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="messages")
public class Message implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="author")
    private User author;
    private String content;


    @ManyToOne
    @JoinColumn(name="conversation")
    private Conversation conversation;

    private String date;

    public Message(){}
    public Message(User author, String content, String date)
    {
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
