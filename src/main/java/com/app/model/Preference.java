package com.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "preferences")
public class Preference implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preference")
    private Long id;

    @Column(name = "artist")
    private String favArtist;
    @Column(name = "genre")
    private String favGenre;

    @OneToOne
    @JoinColumn(name = "id_song")
    private Song favSong;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Preference(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavArtist() {
        return favArtist;
    }

    public void setFavArtist(String favArtist) {
        this.favArtist = favArtist;
    }

    public String getFavGenre() {
        return favGenre;
    }

    public void setFavGenre(String favGenre) {
        this.favGenre = favGenre;
    }

    public Song getFavSong() {
        return favSong;
    }

    public void setFavSong(Song favSong) {
        this.favSong = favSong;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSongTitle() {
        return this.favSong.getTitle();
    }
    public String getSongArtist() {
        return this.favSong.getArtist();
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", favArtist='" + favArtist + '\'' +
                ", favGenre='" + favGenre + '\'' +
                ", favSong=" + favSong +
                '}';
    }
}
