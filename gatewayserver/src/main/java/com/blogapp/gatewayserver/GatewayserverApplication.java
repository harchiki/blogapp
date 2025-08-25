package com.blogapp.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/blogapp/account/serviceInfo")
                        .filters(f -> f.setPath("/serviceInfo")
                                .circuitBreaker(config ->
                                        config.setName("accountInfoCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNT"))
                .route(p -> p
                        .path("/blogapp/account", "/blogapp/account/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ACCOUNT"))

                .route(p -> p
                        .path("/blogapp/post/serviceInfo")
                        .filters(f -> f.setPath("/serviceInfo")
                                .circuitBreaker(config ->
                                        config.setName("accountInfoCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://POST"))
                .route(p -> p
                        .path("/blogapp/post/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://POST"))

                .route(p -> p
                        .path("/blogapp/comment/serviceInfo")
                        .filters(f -> f.setPath("/serviceInfo")
                                .circuitBreaker(config ->
                                        config.setName("accountInfoCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://COMMENT"))
                .route(p -> p
                        .path("/blogapp/comment", "/blogapp/comment/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://COMMENT"))
                .build();
    }
}
