package com.oplayer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer releaseYear;
    private Long duration; // in milliseconds
    private String filePath;
    private String fileType;
    private Long fileSize; // in bytes
    private String artworkPath; // path to album artwork if available
}
