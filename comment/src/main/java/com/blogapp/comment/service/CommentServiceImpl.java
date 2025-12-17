package com.blogapp.comment.service;

import com.blogapp.comment.dto.CommentDto;
import com.blogapp.comment.dto.UpdateNicknameDto;
import com.blogapp.comment.entity.Comment;
import com.blogapp.comment.repository.CommentRepository;
import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final StreamBridge bridge;

    @Override
    public void createComment(CommentDto commentDto) {
        Optional<Comment> optComment = commentRepository.findById(commentDto.getId());

        if (optComment.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        Comment newComment = new Comment();
        modelMapper.map(commentDto, newComment);

        commentRepository.save(newComment);
    }


    @Override
    @Transactional
    public void updateCommentNickname(UpdateNicknameDto nicknameDto) {
        List<Comment> comments = commentRepository.findByNickname(nicknameDto.getOldNickname());
        try {
            if (!comments.isEmpty()) {
                comments.forEach(comment -> comment.setNickname(nicknameDto.getNewNickname()));

                commentRepository.saveAll(comments);
                log.info("Updated all comments with new nickname : {}", nicknameDto.getNewNickname());
            } else {
                log.info("No comment to be updated with new nickname");
            }
        } catch (Exception e) {
            log.error("Error occurred while updating comments with new nickname : {}, error : {}",
                    nicknameDto.getNewNickname(), e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rollbackPostNickname(nicknameDto);
        }
    }

    private void rollbackPostNickname(UpdateNicknameDto nicknameDto) {
        log.info("Sending rollbackPostNickname request for the details: {}", nicknameDto);
        var result = bridge.send("rollbackPostNickname-out-0", nicknameDto);
        log.info("Is the rollbackCardMobileNumber request successfully triggered ? : {}", result);
    }

    @Override
    public void updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setContent(commentDto.getContent());
        comment.setPostId(commentDto.getPostId());
        comment.setNickname(commentDto.getNickname());

        commentRepository.save(comment);
    }

    @Override
    public CommentDto findComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        CommentDto commentDto = new CommentDto();

        modelMapper.map(comment, commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> findCommentByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return modelMapper.map(comments, new TypeToken<List<CommentDto>>() {}.getType());
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        commentRepository.delete(comment);
    }
}
