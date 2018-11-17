package com.app.repository;

import com.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
List<Song> findByArtistContaining(String artist);
List<Song> findByAlbumContaining(String album);
List<Song> findByTitleContaining(String title);
List<Song> findAllByGenre(String genre);
}
