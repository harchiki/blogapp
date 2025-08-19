package com.blogapp.post.service;

import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.post.dto.AccountDto;
import com.blogapp.post.dto.CommentDto;
import com.blogapp.post.dto.PostDetailDto;
import com.blogapp.post.dto.PostDto;
import com.blogapp.post.entity.Post;
import com.blogapp.post.repository.PostRepository;
import com.blogapp.post.service.client.AccountFeignClient;
import com.blogapp.post.service.client.CommentFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentFeignClient commentFeignClient;
    private final AccountFeignClient accountFeignClient;
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
    public PostDetailDto viewPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        PostDetailDto postDto = new PostDetailDto();

        modelMapper.map(post, postDto);

        ResponseEntity<AccountDto> accountResponse = accountFeignClient.getAccount(post.getWriterId());
        postDto.setWriter(accountResponse.getBody());

        ResponseEntity<List<CommentDto>> commentResponse = commentFeignClient.getComments(id);
        postDto.setComments(commentResponse.getBody());

        return postDto;
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        postRepository.delete(post);
    }
}
