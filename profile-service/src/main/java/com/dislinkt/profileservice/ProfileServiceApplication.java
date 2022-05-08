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
//			Profile p1 = new Profile(
//					"dare",
//					"Darko",
//					"Panic",
//					"dare@gmail.com",
//					"+38190933415",
//					Gender.MALE,
//					new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime(),
//					"Doesn't my bio sound like a ballad?",
//					null,
//					null,
//					null,
//					null,
//					ProfileType.PUBLIC
//			);
//			Profile p2 = new Profile(
//					"aki",
//					"Aleksa",
//					"Vujisic",
//					"aki@gmail.com",
//					"+38160433315",
//					Gender.MALE,
//					new GregorianCalendar(1999, Calendar.SEPTEMBER, 17).getTime(),
//					"Mastering fullstack development is my mission.",
//					null,
//					null,
//					null,
//					null,
//					ProfileType.PUBLIC
//			);
//			Profile p3= new Profile(
//					"mel",
//					"Melanija",
//					"Racic",
//					"mel@gmail.com",
//					"+381604458914",
//					Gender.FEMALE,
//					new GregorianCalendar(1999, Calendar.MAY, 16).getTime(),
//					"Backend is my passion.",
//					null,
//					null,
//					null,
//					null,
//					ProfileType.PUBLIC
//			);
//			Profile p4= new Profile(
//					"srki",
//					"Srdjan",
//					"Dragovic",
//					"srki@gmail.com",
//					"+381604458914",
//					Gender.MALE,
//					new GregorianCalendar(1999, Calendar.OCTOBER, 13).getTime(),
//					"I love anime!",
//					null,
//					null,
//					null,
//					null,
//					ProfileType.PRIVATE
//			);
//
//			repository.deleteAll();
//			repository.insert(p1);
//			repository.insert(p2);
//			repository.insert(p3);
//			repository.insert(p4);
		};
	}

}
