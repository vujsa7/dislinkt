package com.dislinkt.postservice;

import com.dislinkt.postservice.dao.PostRepository;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.model.PostType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@EnableEurekaClient
public class PostServiceApplication {

	@Bean
	@LoadBalanced
	WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (PostRepository repository){
		return args -> {
//			Post post = new Post(
//					"0",
//					"Ovo je moj prvi post",
//					"627649c38a6dca73821e84bb",
//					new Date(),
//					PostType.TEXT
//			);

			Post post = new Post(
					"0",
					"0",
					"Moj prvi post!!!",
					PostType.TEXT);


			repository.deleteAll();
			repository.insert(post);
		};
	}
}
