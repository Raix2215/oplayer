package com.oplayer.service;

import com.oplayer.model.Music;
import com.oplayer.repository.MusicRepository;
import com.oplayer.util.AudioUtil;
import com.oplayer.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicService {

    private final MusicRepository musicRepository;
    private final AudioUtil audioUtil;
    private final FileUtil fileUtil;

    @Value("${music.upload.dir:${user.home}/oplayer/music}")
    private String musicUploadDir;

    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    public Optional<Music> getMusicById(Long id) {
        return musicRepository.findById(id);
    }

    public List<Music> searchMusic(String query) {
        List<Music> results = musicRepository.findByTitleContainingIgnoreCase(query);
        results.addAll(musicRepository.findByArtistContainingIgnoreCase(query));
        results.addAll(musicRepository.findByAlbumContainingIgnoreCase(query));
        return results.stream().distinct().toList();
    }

    public Music saveMusic(MultipartFile file) throws IOException {
        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(musicUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file to disk
        String fileName = fileUtil.generateUniqueFileName(file.getOriginalFilename());
        String filePath = uploadPath.resolve(fileName).toString();
        file.transferTo(new File(filePath));

        // Extract audio metadata
        Music music;
        try {
            music = audioUtil.extractMetadata(filePath);
        } catch (Exception e) {
            log.error("Failed to extract metadata from {}: {}", filePath, e.getMessage());
            throw new IOException("Failed to extract metadata", e);
        }

        // Save to database
        return musicRepository.save(music);
    }

    public List<Music> scanDirectory(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new IOException("Invalid directory path: " + directoryPath);
        }

        List<Path> musicFiles = fileUtil.findMusicFiles(dir);
        List<Music> scannedMusic = musicFiles.stream()
                .map(path -> {
                    try {
                        return audioUtil.extractMetadata(path.toString());
                    } catch (Exception e) {
                        log.error("Failed to extract metadata from {}: {}", path, e.getMessage());
                        return null;
                    }
                })
                .filter(music -> music != null)
                .toList();

        return musicRepository.saveAll(scannedMusic);
    }

    public byte[] getAudioFile(Long id) throws IOException {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Music not found with id: " + id));

        return Files.readAllBytes(Paths.get(music.getFilePath()));
    }

    public void deleteMusic(Long id) {
        musicRepository.deleteById(id);
    }
}
