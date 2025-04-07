package com.huangzizhu.service;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.comment.Comment;
import com.huangzizhu.pojo.comment.CommentQueryForm;

public interface CommentService {
    void addComment(Comment comment);

    void like(Integer id);

    void dislike(Integer id);

    QueryResult<Comment> getCommentBySongId(CommentQueryForm param);

    QueryResult<Comment> getCommentByUserId(CommentQueryForm param);

    void deleteComment(Integer id);
}
