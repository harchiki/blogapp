package com.blogapp.comment.service;

import com.blogapp.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    void createComment(CommentDto CommentDto);
    void updateComment(Long id, CommentDto CommentDto);
    CommentDto findComment(Long id);
    List<CommentDto> findCommentByPostId(Long postId);
    void delete(Long id);
}
