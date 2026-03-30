package com.example.hospital.security;

import com.example.hospital.patient.Patient;
import com.example.hospital.patient.PatientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;

    // ✅ MANUAL CONSTRUCTOR
    public CustomUserDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new User(
                patient.getEmail(),
                patient.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + patient.getRole().name()))
        );
    }
}