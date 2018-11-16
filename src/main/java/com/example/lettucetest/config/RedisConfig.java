package com.example.lettucetest.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import lombok.extern.slf4j.Slf4j;

/**
 * Lettuce based asynchronous Redis configuration
 * 
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
	log.info("Enter lettuceConnectionFactory");
	return new LettuceConnectionFactory(redisConfig(), lettuceConfig());
    }
    
    @Bean
    public ReactiveRedisTemplate<String, Parent> reactiveJsonRedisTemplate(
	    ReactiveRedisConnectionFactory factory) {
	log.info("Enter reactiveJsonHotelRedisTemplate");

	Jackson2JsonRedisSerializer<Parent> serializer = new Jackson2JsonRedisSerializer<>(
		Parent.class);
	//serializer.setObjectMapper(objectMapper());
	RedisSerializationContextBuilder<String, Parent> builder = RedisSerializationContext
		.newSerializationContext(new StringRedisSerializer());
	RedisSerializationContext<String, Parent> serializationContext = builder.hashValue(serializer)
		.build();

	return new ReactiveRedisTemplate<>(factory, serializationContext);
    }

   /* @Bean
    public ObjectMapper objectMapper() {
	ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);//so that date comes as ISO 8601
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }*/

    private LettuceClientConfiguration lettuceConfig() {
	return LettuceClientConfiguration.builder().commandTimeout(Duration.ofSeconds(2))
		.shutdownTimeout(Duration.ZERO).build();
    }

    private RedisStandaloneConfiguration redisConfig() {
	RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(
		"localhost", 6379);
	return redisConfig;
    }

}
