package com.example.lettucetest;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LettucetestApplication {

	public static void main(String[] args) {
	    new SpringApplicationBuilder()
		.sources(LettucetestApplication.class)
		.web(WebApplicationType.REACTIVE)
		.bannerMode(Banner.Mode.OFF)
		.run(args);
	}
}
