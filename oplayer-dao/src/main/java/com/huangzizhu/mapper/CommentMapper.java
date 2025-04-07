package com.huangzizhu.mapper;

import com.huangzizhu.pojo.comment.Comment;
import com.huangzizhu.pojo.comment.CommentQueryForm;

import java.util.List;

public interface CommentMapper {
    void addComment(Comment comment);

    Comment getCommentById(Integer commentId);

    void like(Comment comment);

    Integer getCountBySongId(CommentQueryForm param);

    List<Comment> getCommentBySongId(CommentQueryForm param);

    Integer getCountByUserId(CommentQueryForm param);

    List<Comment> getCommentByUserId(CommentQueryForm param);

    void deleteComment(Integer id);
}
