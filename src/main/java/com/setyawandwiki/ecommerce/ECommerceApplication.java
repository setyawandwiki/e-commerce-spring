package com.setyawandwiki.ecommerce;

import com.setyawandwiki.ecommerce.entity.Gender;
import com.setyawandwiki.ecommerce.entity.Role;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class ECommerceApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setGender(Gender.MALE);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
