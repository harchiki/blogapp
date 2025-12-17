package com.blogapp.post.service;

import com.blogapp.post.dto.PostDto;
import com.blogapp.post.entity.UpdateNicknameDto;

public interface PostService {
    void createPost(PostDto postDto);
    void updatePost(Long id, PostDto postDto);
    void updatePostNickname(UpdateNicknameDto nicknameDto);
    PostDto findPost(Long id);
    void delete(Long id);
    void rollbackPostNickname(UpdateNicknameDto updateNickname);
}
