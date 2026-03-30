package com.example.hospital.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	boolean existsByDoctorId(Long doctorId);

    // =========================
    // CHECK DOUBLE BOOKING
    // =========================
    boolean existsByDoctorIdAndDateAndTime(
            Long doctorId,
            LocalDate date,
            LocalTime time
    );

    // =========================
    // GET APPOINTMENTS BY DOCTOR
    // =========================
    List<Appointment> findByDoctorId(Long doctorId);

    // =========================
    // GET APPOINTMENTS BY PATIENT
    // =========================
    List<Appointment> findByPatientId(Long patientId);

    // =========================
    // FILTER BY STATUS
    // =========================
    List<Appointment> findByStatus(AppointmentStatus status);

    // =========================
    // FILTER BY DOCTOR + STATUS
    // =========================
    List<Appointment> findByDoctorIdAndStatus(
            Long doctorId,
            AppointmentStatus status
    );

    // =========================
    // FILTER BY PATIENT + STATUS
    // =========================
    List<Appointment> findByPatientIdAndStatus(
            Long patientId,
            AppointmentStatus status
    );

    // =========================
    // ADMIN DASHBOARD COUNT
    // =========================
    long countByStatus(AppointmentStatus status);
    
    List<Appointment> findByDateAndStatus(
            LocalDate date,
            AppointmentStatus status
    );
}