package com.prodapt.licensetracker.config;

import com.prodapt.licensetracker.entity.User;
import com.prodapt.licensetracker.entity.UserRole;
import com.prodapt.licensetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("admin@prodapt.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@prodapt.com");

                // ✅ BCrypt encoded password
                admin.setPassword(passwordEncoder.encode("Admin@123"));

                admin.setRole(UserRole.ADMIN);
                admin.setActive(true);

                userRepository.save(admin);
            }
        };
    }
}
