package com.blogapp.gatewayserver.handler;

import com.blogapp.exception.EntityNotFoundException;
import com.blogapp.gatewayserver.dto.UserDto;
import com.blogapp.gatewayserver.dto.CommentDto;
import com.blogapp.gatewayserver.dto.PostDetailDto;
import com.blogapp.gatewayserver.dto.PostDto;
import com.blogapp.gatewayserver.service.client.FetchingClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostCompositeHandler {
    private final FetchingClient fetchingClient;
    private final ModelMapper modelMapper = new ModelMapper();

    public Mono<ServerResponse> fetchPostDetails(ServerRequest serverRequest) {
        String postId = serverRequest.pathVariable("post-id");
        Long id = Long.valueOf(postId);
        Mono<ResponseEntity<PostDto>> postDto = fetchingClient.fetchPost(id);

        return postDto
                .flatMap(postResponse -> {
                    // retrieve post's writer nickname
                    String writerNickname = Optional.ofNullable(postResponse.getBody())
                            .orElseThrow(EntityNotFoundException::new)
                            .getNickname();

                    // retrieve writer
                    Mono<ResponseEntity<UserDto>> userMono = fetchingClient.fetchUser(writerNickname);

                    // comments
                    Mono<ResponseEntity<List<CommentDto>>> commentsMono = fetchingClient.fetchComments(id);

                    return Mono.zip(
                            postDto,
                            userMono,
                            commentsMono
                    );
                })
                .flatMap(tuple -> {
                    PostDto post = tuple.getT1().getBody();
                    UserDto user = tuple.getT2().getBody();
                    List<CommentDto> comments = tuple.getT3().getBody();

                    PostDetailDto detail = new PostDetailDto();
                    modelMapper.map(post, detail);
                    detail.setWriter(user);
                    detail.setComments(comments);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(detail);
                });

    }
}
