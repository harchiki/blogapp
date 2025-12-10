package com.blogapp.gatewayserver.router;

import com.blogapp.gatewayserver.handler.PostCompositeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class PostDetailCompositeRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PostCompositeHandler customerCompositeHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/blogapp/blog/{post-id}"),
                customerCompositeHandler::fetchPostDetails);
    }
}
