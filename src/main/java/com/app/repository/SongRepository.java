package com.app.repository;

import com.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
List<Song> findByStatusAndArtistContaining(Song.Status status, String artist);
List<Song> findByStatusAndAlbumContaining(Song.Status status, String album);
List<Song> findByStatusAndTitleContaining(Song.Status status, String title);
List<Song> findAllByStatusAndGenre(Song.Status status, String genre);
List<Song> findAllByStatus(Song.Status status);
}
