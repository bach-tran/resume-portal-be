package com.bach.resumeportal;

import com.bach.resumeportal.dao.UserRepository;
import com.bach.resumeportal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResumePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumePortalApplication.class, args);
	}

	@Bean
	public CommandLineRunner populate(UserRepository userRepo) {
		return s -> {
			User user1 = new User("bach_tran", "password12345", "Bach", "Tran");
			userRepo.save(user1);

			User user2 = new User("minh_minh", "password", "Minh", "Minh");
			userRepo.save(user2);
		};
	}

}
