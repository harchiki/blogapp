package com.blogapp.post.service;

import com.blogapp.post.dto.PostDto;

public interface PostService {
    void createPost(PostDto postDto);
    void updatePost(Long id, PostDto postDto);
    PostDto findPost(Long id);
    void delete(Long id);
}
