package com.dislinkt.connectionservice;

import com.dislinkt.connectionservice.dao.ConnectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@EnableEurekaClient
public class ConnectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectionServiceApplication.class, args);
	}
}
