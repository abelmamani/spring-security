package com.spring.security.spring.boot.jwt;

import com.spring.security.spring.boot.jwt.persistence.entity.ERole;
import com.spring.security.spring.boot.jwt.persistence.entity.RoleEntity;
import com.spring.security.spring.boot.jwt.persistence.entity.UserEntity;
import com.spring.security.spring.boot.jwt.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Bean
	CommandLineRunner init(){
		return args -> {
			if(!userRepository.existsByUsername("admin")){
				UserEntity user = UserEntity.builder()
						.id(null)
						.username("admin")
						.password(passwordEncoder.encode("12345678"))
						.roles(
								Arrays.stream(ERole.values())
										.map(role -> RoleEntity.builder()
												.id(null)
												.name(role)
												.build())
										.collect(Collectors.toSet())
						)
						.build();
				userRepository.save(user);
			}
		};
	}


}
