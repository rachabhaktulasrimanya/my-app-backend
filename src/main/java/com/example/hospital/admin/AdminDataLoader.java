package com.example.hospital.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class AdminDataLoader implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataLoader(AdminRepository adminRepository,
                           PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if(adminRepository.findByEmail("admin@gmail.com").isEmpty()){

            Admin admin = new Admin();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));

            adminRepository.save(admin);
        }
    }
}