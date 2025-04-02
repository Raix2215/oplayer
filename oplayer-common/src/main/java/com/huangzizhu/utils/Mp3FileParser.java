package com.huangzizhu.app.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Mp3FileParser {

    public static String readMP3Tags(File file) {
        StringBuilder tagsInfo = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(file)) {
            // 创建 Player 对象，用于读取 MP3 文件
            Player player = new Player(inputStream);
            // 获取标签信息
            Tag tag = player.getTag();
            if (tag != null) {
                tagsInfo.append("Title: ").append(tag.getTitle()).append("\n");
                tagsInfo.append("Artist: ").append(tag.getArtist()).append("\n");
                tagsInfo.append("Album: ").append(tag.getAlbum()).append("\n");
                tagsInfo.append("Year: ").append(tag.getYear()).append("\n");
                tagsInfo.append("Comment: ").append(tag.getComment()).append("\n");
                tagsInfo.append("Track: ").append(tag.getTrack()).append("\n");
                tagsInfo.append("Genre: ").append(tag.getGenreDescription()).append("\n");
            } else {
                tagsInfo.append("No ID3 tag found in the MP3 file.");
            }
        } catch (TagException e) {
            tagsInfo.append("Error reading tags: ").append(e.getMessage());
        } catch (JavaLayerException e) {
            tagsInfo.append("Error processing MP3 file: ").append(e.getMessage());
        } catch (Exception e) {
            tagsInfo.append("Error: ").append(e.getMessage());
        }
        return tagsInfo.toString();
    }

    public static void main(String[] args) {
        // 示例：读取 MP3 文件并打印标签信息
        String filePath = "path/to/your/mp3file.mp3"; // 替换为你的 MP3 文件路径
        String tagsInfo = readMP3Tags(filePath);
        System.out.println(tagsInfo);
    }
}
