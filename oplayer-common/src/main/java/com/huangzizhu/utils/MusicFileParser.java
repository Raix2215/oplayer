package com.huangzizhu.utils;

import com.aliyuncs.exceptions.ClientException;
import com.huangzizhu.exception.MusicParseException;
import com.huangzizhu.exception.UnsupportedMusicFormat;
import com.huangzizhu.pojo.Song;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class MusicFileParser {
    private final int MP3 = 1;
    private final int WAV = 2;
    private final int FLAC = 3;
    @Autowired
    private AliOSSOperator aliOSSOperator;
    @Autowired
    @Qualifier("MusicOperationExecutor")  // 复用之前的线程池
    private Executor executor;

    public Song mp3Parse(File path) {
        try {
            return getSong(path, MP3);
        } catch (Exception e) {
            throw new MusicParseException("解析mp3文件时发生错误", path, e);
        }
    }

    public Song wavParse(File path) {
        try {
            return getSong(path, WAV);
        } catch (Exception e) {
            throw new MusicParseException("解析wav文件时发生错误", path, e);
        }
    }

    public Song flacParse(File path) {
        try {
            return getSong(path, FLAC);
        } catch (Exception e) {
            throw new MusicParseException("解析flac文件时发生错误", path, e);
        }
    }
    // 新增异步解析方法（返回 CompletableFuture）
    public CompletableFuture<Song> parseAsync(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (file.getName().endsWith(".mp3")) {
                    return mp3Parse(file);
                } else if (file.getName().endsWith(".wav")) {
                    return wavParse(file);
                } else if (file.getName().endsWith(".flac")) {
                    return flacParse(file);
                }
                throw new UnsupportedMusicFormat("不支持的音乐格式");
            } catch (Exception e) {
                throw new MusicParseException("解析文件失败", file, e);
            }
        }, executor);
    }

    private Song getSong(File path, int type) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, ClientException {
        AudioFile audioFile = AudioFileIO.read(path);
        AudioHeader audioHeader = audioFile.getAudioHeader();
        Tag tag = audioFile.getTag();
        // 获取音频文件的基本信息
        String name = tag.getFirst(FieldKey.TITLE);
        String artist = tag.getFirst(FieldKey.ARTIST);
        String albumName = tag.getFirst(FieldKey.ALBUM);
        String year = tag.getFirst(FieldKey.YEAR);
        Integer duration = audioHeader.getTrackLength();
        Integer sampleRate = audioHeader.getSampleRateAsNumber();
        Integer bitRate = Long.valueOf(audioHeader.getBitRateAsNumber()).intValue();
        Integer size = Integer.parseInt(String.valueOf(path.length()));
        String coverUrl = null;
        String format = null;
        switch (type) {
            case MP3 -> {
                coverUrl = getMp3ImgUrl((AbstractID3v2Tag) tag, name, path);
                format = "mp3";
            }
            case WAV -> {
                coverUrl = getWavImgUrl((AbstractID3v2Tag) tag, name, path);
                format = "wav";
            }
            case FLAC -> {
                coverUrl = getFlacImgUrl(audioFile, name, path);
                format = "flac";
            }
        }
        //对name,artist,albumName未读取到进行处理
        String[] info = {name, artist, albumName};
        processUnreadableInfo(path, info);
        return new Song
                (null, info[0], info[1], null, info[2],
                        year, duration, format, size, coverUrl,
                        bitRate, sampleRate, null, null, true);
    }

    private void processUnreadableInfo(File file, String[] info) {
        if (info == null || info.length != 3) {
            return;
        }
        if (info[0] == null || info[0].isBlank() || info[1] == null || info[1].isBlank()) {
            //根据文件名尝试获取
            String[] strings = extractArtistAndName(CommonUtils.getFileNameWithoutExtension(file));
            info[0] = strings[1];//info0是name,strings[1]是name
            info[1] = strings[0];
        }
        if (info[2] == null || info[2].isBlank()) {
            info[2] = "unknown";
        }
    }

    public String[] extractArtistAndName(String input) {
        String[] result = new String[2];

        if (input.contains(" - ")) {
            // 第一种情况：有" - "分隔符（带空格）
            String[] parts = input.split(" - ", 2);
            result[0] = parts[0].trim();
            result[1] = parts[1].trim();
        } else if (input.contains("-")) {
            // 第二种情况：有"-"分隔符（不带空格）
            String[] parts = input.split("-", 2);
            result[0] = parts[0].trim();
            result[1] = parts[1].trim();
        } else {
            // 第三种情况：没有分隔符
            result[0] = "unknown";
            result[1] = input.trim();
        }
        return result;
    }


    private String getFlacImgUrl(AudioFile audioFile, String name, File path) {
        // 获取封面图片
        Artwork artwork = audioFile.getTag().getFirstArtwork();
        if (artwork != null) {
            byte[] imageData = artwork.getBinaryData();
            try {
                return new AliOSSOperator().uploadFile(imageData, name);
            } catch (ClientException e) {
                throw new MusicParseException("阿里云上传封面出现异常", path, e);
            }
        }
        return null;
    }

    private String getWavImgUrl(AbstractID3v2Tag tag, String name, File path) {
        return getMp3ImgUrl(tag, name, path);
    }

    private String getMp3ImgUrl(AbstractID3v2Tag tag, String name, File path) {
        // 获取封面图片
        AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
        if (frame != null) {
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            byte[] imageData = body.getImageData();
            try {
                return aliOSSOperator.uploadFile(imageData, name);
            } catch (ClientException e) {
                throw new MusicParseException("阿里云上传封面出现异常", path, e);
            }
        }
        return null;
    }

}