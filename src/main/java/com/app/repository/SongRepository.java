package com.app.repository;

import com.app.model.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByStatusAndArtistContaining(Song.Status status, String artist);
    List<Song> findAllByStatusAndAlbumContaining(Song.Status status, String album);
    List<Song> findAllByStatusAndTitleContaining(Song.Status status, String title);
    List<Song> findAllByStatusAndGenre(Song.Status status, String genre);


    List<Song> findByStatusAndArtistContaining(Song.Status status, String artist, Pageable pageable);
    List<Song> findByStatusAndAlbumContaining(Song.Status status, String album, Pageable pageable);
    List<Song> findByStatusAndTitleContaining(Song.Status status, String title, Pageable pageable);
    List<Song> findByStatusAndGenre(Song.Status status, String genre, Pageable pageable);


    List<Song> findAllByStatus(Song.Status status);
    List<Song> findByStatus(Song.Status status, Pageable pageable);

    Song findByArtistAndTitle(String artist, String title);
}
