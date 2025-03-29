package com.oplayer.util;

import com.oplayer.model.Music;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class AudioUtil {

    public Music extractMetadata(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            throw new IOException("Cannot read file: " + filePath);
        }

        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();

        String title = getTagField(tag, FieldKey.TITLE);
        if (title.isEmpty()) {
            title = file.getName();
        }

        String fileType = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();

        return Music.builder()
                .title(title)
                .artist(getTagField(tag, FieldKey.ARTIST))
                .album(getTagField(tag, FieldKey.ALBUM))
                .genre(getTagField(tag, FieldKey.GENRE))
                .releaseYear(parseYear(getTagField(tag, FieldKey.YEAR)))
                .duration(Long.valueOf(audioFile.getAudioHeader().getTrackLength() * 1000))
                .filePath(filePath)
                .fileType(fileType)
                .fileSize(file.length())
                .build();
    }

    private String getTagField(Tag tag, FieldKey fieldKey) {
        try {
            if (tag == null) {
                return "";
            }
            String value = tag.getFirst(fieldKey);
            return value != null ? value : "";
        } catch (Exception e) {
            return "";
        }
    }

    private Integer parseYear(String yearStr) {
        try {
            if (yearStr != null && !yearStr.isEmpty()) {
                return Integer.parseInt(yearStr);
            }
        } catch (NumberFormatException e) {
            // Ignore parse exceptions
        }
        return null;
    }

    public byte[] extractAlbumArt(String filePath) {
        try {
            File file = new File(filePath);
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            if (tag != null && tag.getFirstArtwork() != null) {
                return tag.getFirstArtwork().getBinaryData();
            }
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
            log.error("Failed to extract album art from {}: {}", filePath, e.getMessage());
        }
        return null;
    }
}
