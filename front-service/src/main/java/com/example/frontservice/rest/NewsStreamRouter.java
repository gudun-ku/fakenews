package com.example.frontservice.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NewsStreamRouter {

    @Bean
    RouterFunction<?> routes(NewsHandler handler) {
        return  route(RequestPredicates
                        .GET("/news/stream"),
                        handler::streamNews)
                .and(
                route(RequestPredicates
                                .POST("/news/all"),
                        handler::allNews));
    }
}
