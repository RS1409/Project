package com.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long id;

    private String content;
    private String date;
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "`post_comments`")
    private Post post;



    public Comment() {
    }

    public Comment(String content, String date, String autorName) {
        this.content = content;
        this.date = date;
        this.authorName = autorName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", autorName='" + authorName + '\'' +
                ", post=" + post +
                '}';
    }
}
