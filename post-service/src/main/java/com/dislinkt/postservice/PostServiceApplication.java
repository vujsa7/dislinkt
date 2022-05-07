package com.dislinkt.postservice;

import com.dislinkt.postservice.dao.PostRepository;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.model.PostType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@EnableEurekaClient
public class PostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (PostRepository repository){
		return args -> {
			Post post = new Post(
					"627649c38a6dca73821e84bb",
					"Ovo je moj prvi post",
					new Date(),
					PostType.TEXT
			);

			repository.insert(post);
		};
	}

}
