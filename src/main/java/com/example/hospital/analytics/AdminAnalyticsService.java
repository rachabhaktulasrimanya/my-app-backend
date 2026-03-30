package com.example.hospital.analytics;

import com.example.hospital.appointment.AppointmentRepository;
import com.example.hospital.appointment.AppointmentStatus;
import com.example.hospital.doctor.DoctorRepository;
import com.example.hospital.patient.PatientRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminAnalyticsService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AdminAnalyticsService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public long totalAppointments(){
        return appointmentRepository.count();
    }

    public long completedAppointments(){
        return appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);
    }

    public long pendingAppointments(){
        return appointmentRepository.countByStatus(AppointmentStatus.PENDING);
    }

    public long approvedAppointments(){
        return appointmentRepository.countByStatus(AppointmentStatus.APPROVED);
    }

    public long rejectedAppointments(){
        return appointmentRepository.countByStatus(AppointmentStatus.REJECTED);
    }

    public long totalDoctors(){
        return doctorRepository.count();
    }

    public long totalPatients(){
        return patientRepository.count();
    }
}