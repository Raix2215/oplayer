package com.oplayer.controller;

import com.oplayer.model.Music;
import com.oplayer.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
@CrossOrigin
public class MusicController {

    private final MusicService musicService;

    @GetMapping
    public ResponseEntity<List<Music>> getAllMusic() {
        return ResponseEntity.ok(musicService.getAllMusic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> getMusicById(@PathVariable Long id) {
        return musicService.getMusicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Music>> searchMusic(@RequestParam String query) {
        return ResponseEntity.ok(musicService.searchMusic(query));
    }

    @PostMapping("/upload")
    public ResponseEntity<Music> uploadMusic(@RequestParam("file") MultipartFile file) {
        try {
            Music savedMusic = musicService.saveMusic(file);
            return ResponseEntity.ok(savedMusic);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/scan")
    public ResponseEntity<List<Music>> scanDirectory(@RequestParam String directoryPath) {
        try {
            List<Music> scannedMusic = musicService.scanDirectory(directoryPath);
            return ResponseEntity.ok(scannedMusic);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/play/{id}")
    public ResponseEntity<Resource> playMusic(@PathVariable Long id) {
        try {
            Music music = musicService.getMusicById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Music not found"));

            byte[] audioBytes = musicService.getAudioFile(id);
            ByteArrayResource resource = new ByteArrayResource(audioBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "inline;filename=" + music.getTitle() + "." + music.getFileType());

            MediaType mediaType;
            switch (music.getFileType().toLowerCase()) {
                case "mp3":
                    mediaType = MediaType.parseMediaType("audio/mpeg");
                    break;
                case "wav":
                    mediaType = MediaType.parseMediaType("audio/wav");
                    break;
                case "ogg":
                    mediaType = MediaType.parseMediaType("audio/ogg");
                    break;
                case "flac":
                    mediaType = MediaType.parseMediaType("audio/flac");
                    break;
                default:
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(music.getFileSize())
                    .contentType(mediaType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id) {
        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }
}
