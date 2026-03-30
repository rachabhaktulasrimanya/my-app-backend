package com.example.hospital.admin;

import com.example.hospital.appointment.AppointmentRepository;
import com.example.hospital.appointment.AppointmentStatus;
import com.example.hospital.doctor.DoctorRepository;
import com.example.hospital.patient.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminService(PatientRepository patientRepository,
                        DoctorRepository doctorRepository,
                        AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Map<String, Object> getDashboardStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalPatients", patientRepository.count());
        stats.put("totalDoctors", doctorRepository.count());
        stats.put("totalAppointments", appointmentRepository.count());

        stats.put("pendingAppointments",
                appointmentRepository.countByStatus(AppointmentStatus.PENDING));

        stats.put("approvedAppointments",
                appointmentRepository.countByStatus(AppointmentStatus.APPROVED));

        stats.put("rejectedAppointments",
                appointmentRepository.countByStatus(AppointmentStatus.REJECTED));

        return stats;
    }
}