package com.example.hospital.doctor;

import com.example.hospital.security.Role;
import com.example.hospital.appointment.AppointmentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository,
                         PasswordEncoder passwordEncoder,
                         AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void deleteDoctor(Long id) {

        if (appointmentRepository.existsByDoctorId(id)) {
            throw new RuntimeException("Doctor has appointments. Cannot delete.");
        }

        doctorRepository.deleteById(id);
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationIgnoreCase(specialization);
    }

    public Doctor saveDoctor(Doctor doctor) {

        if (doctor.getPassword() == null) {
            throw new RuntimeException("Password cannot be null");
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole(Role.DOCTOR);

        return doctorRepository.save(doctor);
    }

    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
    public Page<Doctor> searchDoctorsWithPagination(
            String specialization,
            Pageable pageable
    ) {

        if (specialization == null || specialization.isEmpty()) {
            return doctorRepository.findAll(pageable);
        }

        return doctorRepository
                .findBySpecializationContainingIgnoreCase(
                        specialization,
                        pageable
                );
    }
}