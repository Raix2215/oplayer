package com.huangzizhu.controller;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.music.SimpleMusicInfo;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.music.MusicQueryForm;
import com.huangzizhu.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private SongService songService;

    @PostMapping("/all")
    public Result getAllMD5(@RequestBody MusicQueryForm param) {
        log.info("获取所有音乐{}", param);
        QueryResult<Song> data = songService.getAllMusic(param);
        return Result.success(data);
    }
    @GetMapping("/{id}")
    public Result getMusicById(@PathVariable Integer id) {
        log.info("获取音乐: {}", id);
        Song song = songService.getMusicById(id);
        return Result.success(song);
    }
    @GetMapping("/search")
    public Result fuzzySearch(@RequestParam String name) {
        log.info("模糊查询歌曲 name:{}", name);
        List<SimpleMusicInfo> data = songService.fuzzySearch(name);
        return Result.success(data);
    }


    @GetMapping("/stream/{md5}")
    ResponseEntity<StreamingResponseBody> streamMusic(@PathVariable String md5, @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        log.info("获取音乐流: {}", md5);
        // 获取音乐文件资源
        Resource resource = songService.getMusicResource(md5);

        // 检查资源是否存在
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 处理范围请求(支持断点续传)
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            return songService.handlePartialContent(resource, rangeHeader);
        }

        // 完整文件流传输
        StreamingResponseBody responseBody = outputStream -> {
            try (InputStream inputStream = resource.getInputStream()) {
                inputStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .body(responseBody);
    }
    @GetMapping("/download/{md5}")
    public ResponseEntity<Resource> downloadMusic(@PathVariable String md5) throws IOException {
        log.info("下载音乐: {}", md5);
        return songService.downloadMusic(md5);
    }

}
