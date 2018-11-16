package com.example.lettucetest.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.lettucetest.config.Parent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.embedded.RedisServer;

@RestController
public class LettuceController {
    
    private static RedisServer server = null;

    @Autowired
    ReactiveRedisTemplate<String, Parent> template;

    @PostConstruct
    private void postConstruct() {
	
	try {
	    server = new RedisServer(6379);
	} catch (IOException e) {
	    e.printStackTrace();
	} // bind to a random port
	server.start();

	for (long i = 0; i < 4; i++) {
	    
	    Parent p = new Parent();
	    p.setId(Long.valueOf(i));
	    p.setName(String.valueOf(i));

	    template.opsForHash().put("master-key", "entry-" + i, p).subscribe();
	    //template.opsForValue().set("use-key-" + i, p).subscribe();
	}
    }
    
    @PreDestroy
    private void preDestroy() {
	server.stop();
    }

    public Mono<ServerResponse> get(ServerRequest req) {
	
	List<Parent> pList = new ArrayList<>();
	for (long i = 0; i < 4; i++) {
	    
	    Parent p = new Parent();
	    p.setId(Long.valueOf(i));
	    p.setName(String.valueOf(i));
	    pList.add(p);
	}
	
	Flux<Parent> response = Flux.fromIterable(pList);
	
	return ServerResponse.ok().body(response, Parent.class);
    }
    
    public Mono<ServerResponse> getFromRedis(ServerRequest req) {
	
	Flux<Parent> response = template.<String, Parent>opsForHash().entries("master-key")
		.map(Map.Entry::getValue)
		.doOnSubscribe(e -> System.out.println("onsub " + e))
		.doOnNext(e -> System.out.println("onNext " + e))
		.map(p -> {
		    System.out.println(p);
		    return p;
		});
	
	return ServerResponse.ok().body(response, Parent.class);
    }

}
