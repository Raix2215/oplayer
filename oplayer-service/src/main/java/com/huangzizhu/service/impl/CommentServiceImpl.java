package com.huangzizhu.service.impl;

import com.huangzizhu.exception.CommentException;
import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.mapper.CommentMapper;
import com.huangzizhu.mapper.SongMapper;
import com.huangzizhu.mapper.UserMapper;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.comment.Comment;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.comment.CommentQueryForm;
import com.huangzizhu.pojo.user.User;
import com.huangzizhu.service.CommentService;
import com.huangzizhu.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SongMapper songMapper;

    @Override
    public void addComment(Comment comment) {
        checkSong(comment.getSongId());
        checkUser(comment.getUserId());
        if(CommonUtils.isBlank(comment.getTxt())){
            throw new ParamInvalidException("评论内容不能为空");
        }
        comment.setTime(LocalDateTime.now());
        comment.setCount(0);
        try {
            commentMapper.addComment(comment);
        } catch (Exception e) {
            throw new CommentException("评论失败");
        }
    }

    @Override
    public void like(Integer id) {
        Comment comment = checkComment(id);
        comment.setCount(comment.getCount()+1);
        try {
            commentMapper.like(comment);
        } catch (Exception e) {
            throw new CommentException("点赞失败");
        }
    }

    @Override
    public void dislike(Integer id) {
        Comment comment = checkComment(id);
        comment.setCount(CommonUtils.max(comment.getCount()-1,0));
        try {
            commentMapper.like(comment);
        } catch (Exception e) {
            throw new CommentException("取消点赞失败");
        }
    }

    @Override
    public QueryResult<Comment> getCommentBySongId(CommentQueryForm param) {
        checkSong(param.getSongId());
        Integer total = null;
        List<Comment> list = null;
        try {
            total = commentMapper.getCountBySongId(param);
            // 分页参数
            param.setStart((param.getPage()-1) * param.getPageSize());
            list = commentMapper.getCommentBySongId(param);
        } catch (Exception e) {
            throw new CommentException("获取评论失败");
        }
        return new QueryResult<>(total, list);
    }

    @Override
    public QueryResult<Comment> getCommentByUserId(CommentQueryForm param) {
        checkUser(param.getUserId());
        Integer total = null;
        List<Comment> list = null;
        try {
            total = commentMapper.getCountByUserId(param);
            // 分页参数
            param.setStart((param.getPage()-1) * param.getPageSize());
            list = commentMapper.getCommentByUserId(param);
        } catch (Exception e) {
            throw new CommentException("获取评论失败");
        }
        return new QueryResult<>(total, list);
    }

    @Override
    public void deleteComment(Integer id) {
        checkComment(id);
        try {
            commentMapper.deleteComment(id);
        } catch (Exception e) {
            throw new CommentException("删除评论失败");
        }
    }

    private Song checkSong(Integer songId) {
        Song song = songMapper.getMusicById(songId);
        if(song == null) {
            throw new ParamInvalidException("歌曲不存在");
        }
        return song;
    }

    private User checkUser(Integer userId) {
        User user = userMapper.getUserById(userId);
        if(user == null) {
            throw new ParamInvalidException("用户不存在");
        }
        return user;
    }

    private Comment checkComment(Integer commentId) {
        Comment comment = commentMapper.getCommentById(commentId);
        if(comment == null) {
            throw new ParamInvalidException("评论不存在");
        }
        return comment;
    }
}
