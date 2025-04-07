package com.huangzizhu.controller;

import com.huangzizhu.annotion.AdminCheck;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.comment.Comment;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.comment.CommentQueryForm;
import com.huangzizhu.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        log.info("新增评论:{}", comment);
        commentService.addComment(comment);
        return Result.success();
    }
    @PostMapping("/like/{id}")
    public Result like(@PathVariable Integer id) {
        log.info("点赞评论:{}", id);
        commentService.like(id);
        return Result.success();
    }
    @PostMapping("/dislike/{id}")
    public Result dislike(@PathVariable Integer id) {
        log.info("取消点赞评论:{}", id);
        commentService.dislike(id);
        return Result.success();
    }
    @PostMapping("/music")
    public Result getCommentBySongId(@RequestBody CommentQueryForm param) {
        log.info("获取歌曲评论:{}", param);
        QueryResult<Comment> data = commentService.getCommentBySongId(param);
        return Result.success(data);
    }
    @PostMapping("/user")
    public Result getCommentByUserId(@RequestBody CommentQueryForm param) {
        log.info("获取用户评论:{}", param);
        QueryResult<Comment> data = commentService.getCommentByUserId(param);
        return Result.success(data);
    }
    @AdminCheck
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Integer id) {
        log.info("删除评论:{}", id);
        commentService.deleteComment(id);
        return Result.success();
    }
}
