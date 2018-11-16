package com.example.lettucetest.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.lettucetest.web.LettuceController;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(LettuceController handler) {
	
	return route(GET("/"), handler::get)
		.andRoute(GET("/fromRedis"), handler::getFromRedis);
    }
}
