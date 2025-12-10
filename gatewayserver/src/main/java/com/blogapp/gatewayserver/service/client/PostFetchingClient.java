package com.blogapp.gatewayserver.service.client;

import com.blogapp.gatewayserver.dto.AccountDto;
import com.blogapp.gatewayserver.dto.CommentDto;
import com.blogapp.gatewayserver.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostFetchingClient {
    @GetExchange(value= "/blogapp/post/{id}", accept = "application/json")
    Mono<ResponseEntity<PostDto>> fetchPost(@PathVariable("id") Long id);


    @GetExchange(value= "/blogapp/account/{nickname}", accept = "application/json")
    Mono<ResponseEntity<AccountDto>> fetchAccount(@PathVariable("nickname") String nickname);


    @GetExchange(value= "/blogapp/comment/by-post/{post-id}", accept = "application/json")
    Mono<ResponseEntity<List<CommentDto>>> fetchComments(@PathVariable("post-id") Long postId);

}
