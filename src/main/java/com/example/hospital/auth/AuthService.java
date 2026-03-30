package com.example.hospital.auth;

import com.example.hospital.patient.*;
import com.example.hospital.doctor.*;
import com.example.hospital.security.*;
import com.example.hospital.admin.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (patientRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setPhone(request.getPhone());
        patient.setRole(Role.PATIENT);

        patientRepository.save(patient);

        // ✅ UPDATED
        String token = jwtService.generateToken(
                patient.getId(),
                patient.getEmail(),
                patient.getRole().name()
        );

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        // 1️⃣ CHECK ADMIN FIRST
        Admin admin = adminRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (admin != null) {

            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            // ✅ UPDATED
            String token = jwtService.generateToken(
                    admin.getId(),
                    admin.getEmail(),
                    admin.getRole().name()
            );

            return new AuthResponse(token);
        }

        // 2️⃣ CHECK DOCTOR
        Doctor doctor = doctorRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (doctor != null) {

            if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            // ✅ UPDATED
            String token = jwtService.generateToken(
                    doctor.getId(),
                    doctor.getEmail(),
                    doctor.getRole().name()
            );

            return new AuthResponse(token);
        }

        // 3️⃣ CHECK PATIENT
        Patient patient = patientRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (patient != null) {

            if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            // ✅ UPDATED
            String token = jwtService.generateToken(
                    patient.getId(),
                    patient.getEmail(),
                    patient.getRole().name()
            );

            return new AuthResponse(token);
        }

        throw new RuntimeException("User not found");
    }
}