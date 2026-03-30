package com.example.hospital.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecializationIgnoreCase(String specialization);
    Page<Doctor> findBySpecializationContainingIgnoreCase(
            String specialization,
            Pageable pageable
    );
}