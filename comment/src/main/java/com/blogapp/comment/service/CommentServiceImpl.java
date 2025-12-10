package com.blogapp.comment.service;

import com.blogapp.comment.dto.CommentDto;
import com.blogapp.comment.entity.Comment;
import com.blogapp.comment.repository.CommentRepository;
import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    
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
    public void updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        comment.setContent(commentDto.getContent());
        comment.setPostId(commentDto.getPostId());
        comment.setNickname(commentDto.getNickname());

        commentRepository.save(comment);
    }

    @Override
    public CommentDto findComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
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
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentRepository.delete(comment);
    }
}
