package com.blogapp.comment.controller;

import com.blogapp.comment.dto.CommentDto;
import com.blogapp.comment.service.CommentService;
import com.blogapp.exception.RetryException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        commentService.updateComment(id, commentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findPostById(@PathVariable("id") Long id) {
        CommentDto comment = commentService.findComment(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/by-post/{post-id}")
    ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable("post-id") Long postId) {
        List<CommentDto> comment = commentService.findCommentByPostId(postId);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/version")
    @Retry(name = "getVersion", fallbackMethod = "getVersionFallback")
    public ResponseEntity<String> getVersion() {
        log.warn("getVersion invoked.");
        throw new RetryException ("Retry attempt");
    }

    private ResponseEntity<String> getVersionFallback(Throwable t) {
        return ResponseEntity.internalServerError().body("Service not available, try it again later");
    }
}
