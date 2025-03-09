package com.oplayer.repository;

import com.oplayer.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContainingIgnoreCase(String title);

    List<Music> findByArtistContainingIgnoreCase(String artist);

    List<Music> findByAlbumContainingIgnoreCase(String album);

    List<Music> findByGenreContainingIgnoreCase(String genre);
}
