package com.blogapp.post.service;

import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.EntityNotFoundException;
import com.blogapp.post.dto.PostDto;
import com.blogapp.post.entity.Post;
import com.blogapp.post.entity.UpdateNicknameDto;
import com.blogapp.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final StreamBridge bridge;


    @Override
    public void createPost(PostDto postDto) {
        Optional<Post> optPost = postRepository.findById(postDto.getId());

        if (optPost.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        Post post = new Post();
        modelMapper.map(postDto, post);

        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(postDto, post);

        postRepository.save(post);
    }

    @Override
    @Transactional
    public void updatePostNickname(UpdateNicknameDto nicknameDto) {
        try {
            List<Post> posts = postRepository.findByNickname(nicknameDto.getOldNickname());
            if (!posts.isEmpty()) {
                posts.forEach(post -> post.setNickname(nicknameDto.getNewNickname()));

                postRepository.saveAll(posts);
                log.info("Updated all posts with new nickname : {}", nicknameDto.getNewNickname());
            } else {
                log.info("No post to be updated with new nickname");
            }

            updateCommentNickname(nicknameDto);
        } catch (Exception e) {
            log.error("Error occurred while updating posts with new nickname : {}, error : {}",
                    nicknameDto.getNewNickname(), e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rollbackUserNickname(nicknameDto);
        }
    }

    @Override
    public void rollbackPostNickname(UpdateNicknameDto updateNickname) {
        // retrieve ones just updated with newNickname
        List<Post> posts = postRepository.findByNickname(updateNickname.getNewNickname());

        if (!posts.isEmpty()) {
            posts.forEach(post -> post.setNickname(updateNickname.getOldNickname()));

            postRepository.saveAll(posts);
            log.info("Rollbacked new nickname on all posts : current nickname : {}", updateNickname.getOldNickname());
        } else {
            log.info("No post to rollback with new nickname");
        }

        rollbackUserNickname(updateNickname);
    }

    private void rollbackUserNickname(UpdateNicknameDto nicknameDto) {
        log.info("Sending rollbackUserNickname request for the details: {}", nicknameDto);
        var result = bridge.send("rollbackUserNickname-out-0", nicknameDto);
        log.info("Is the rollbackUserNickname request successfully triggered ? : {}", result);
    }

    private void updateCommentNickname(UpdateNicknameDto nicknameDto) {
        log.info("Sending updateCommentNickname request for the details: {}", nicknameDto);
        var result = bridge.send("updateCommentNickname-out-0", nicknameDto);
        log.info("Is the updateCommentNickname request successfully triggered ? : {}", result);
    }

    @Override
    public PostDto findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        PostDto postDto = new PostDto();

        modelMapper.map(post, postDto);
        return postDto;
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postRepository.delete(post);
    }
}
