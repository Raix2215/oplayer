package com.oplayer.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class FileUtil {

    private static final List<String> SUPPORTED_AUDIO_EXTENSIONS = Arrays.asList(
            ".mp3", ".wav", ".ogg", ".flac", ".aac", ".m4a");

    public String generateUniqueFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);

        return timestamp + "-" + uniqueId + extension;
    }

    public List<Path> findMusicFiles(Path directory) throws IOException {
        try (Stream<Path> pathStream = Files.walk(directory)) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String filename = path.toString().toLowerCase();
                        return SUPPORTED_AUDIO_EXTENSIONS.stream()
                                .anyMatch(filename::endsWith);
                    })
                    .toList();
        }
    }

    public boolean isSupportedAudioFile(String filename) {
        String lowercaseFilename = filename.toLowerCase();
        return SUPPORTED_AUDIO_EXTENSIONS.stream()
                .anyMatch(lowercaseFilename::endsWith);
    }
}
