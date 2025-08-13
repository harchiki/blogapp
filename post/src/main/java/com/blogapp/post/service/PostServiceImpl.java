package com.blogapp.post.service;

import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.post.dto.PostDto;
import com.blogapp.post.entity.Post;
import com.blogapp.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public void createPost(PostDto postDto) {
        Optional<Post> optPost = postRepository.findById(postDto.getId());

        if (optPost.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        Post newAccount = new Post();
        modelMapper.map(postDto, newAccount);

        postRepository.save(newAccount);
    }

    @Override
    public void updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        modelMapper.map(postDto, post);

        postRepository.save(post);
    }

    @Override
    public PostDto findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        PostDto postDto = new PostDto();

        modelMapper.map(post, postDto);
        return postDto;
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        postRepository.delete(post);
    }
}
