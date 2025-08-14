package com.blogapp.comment.service;

import com.blogapp.comment.dto.CommentDto;

public interface CommentService {
    void createComment(CommentDto CommentDto);
    void updateComment(Long id, CommentDto CommentDto);
    CommentDto findComment(Long id);
    void delete(Long id);
}
