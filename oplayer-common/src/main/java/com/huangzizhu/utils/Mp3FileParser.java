package com.huangzizhu.utils;
import com.huangzizhu.exception.InitException;
import com.huangzizhu.pojo.Song;
import com.mpatric.mp3agic.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Mp3FileParser {

    public static Song parse(File path) {
        try {
            Mp3File mp3file = new Mp3File(path);
            if (mp3file.hasId3v2Tag()) {
                return getSong(mp3file);
            }
        } catch (InvalidDataException | UnsupportedTagException | IOException e) {
            throw new InitException("解析MP3文件出错", e);
        }
        return null;
    }

    private static Song getSong(Mp3File mp3file) {
        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
        String name = id3v2Tag.getTitle();
        String artist = id3v2Tag.getArtist();
        String albumName = id3v2Tag.getAlbum();
        String year = id3v2Tag.getYear();
        Integer duration = id3v2Tag.getLength();
        int bitRate = mp3file.getBitrate();
        int sampleRate = mp3file.getSampleRate();
        // 如果有封面图片
        byte[] imageData = id3v2Tag.getAlbumImage();
        if (imageData != null) {
            log.info("有封面图片，大小为：" + imageData.length + " bytes");
        }
        return new Song
                (null, name, artist, null, albumName,
                        year, duration, null, null, null,
                        bitRate, sampleRate, null, null, true);
    }
}
