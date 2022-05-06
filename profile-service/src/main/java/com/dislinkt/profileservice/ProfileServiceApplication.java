package com.dislinkt.profileservice;

import com.dislinkt.profileservice.dao.ProfileRepository;
import com.dislinkt.profileservice.model.Gender;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.model.ProfileType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@EnableEurekaClient
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (ProfileRepository repository){
		return args -> {
			Profile profile1 = new Profile(
					"Username1",
					"Ime1",
					"Prezime1",
					"Email1",
					"064",
					Gender.MALE,
					new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime(),
					"Zar ne zvuci ko balada moja biografija?",
					null,
					null,
					null,
					null,
					ProfileType.PUBLIC
			);

			repository.deleteAll();
			repository.insert(profile1);
		};
	}

}
