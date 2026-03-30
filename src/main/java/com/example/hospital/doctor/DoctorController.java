package com.example.hospital.doctor;

import com.example.hospital.appointment.Appointment;
import com.example.hospital.appointment.AppointmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public DoctorController(DoctorService doctorService,
                            AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    // =========================
    // GET ALL DOCTORS
    // =========================
    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getAllDoctors();
    }

    // =========================
    // CREATE DOCTOR
    // =========================
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    // =========================
    // DELETE DOCTOR
    // =========================
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    // =========================
    // DOCTOR SCHEDULE
    // =========================
    @GetMapping("/{id}/schedule")
    public List<Appointment> getDoctorSchedule(@PathVariable Long id) {
        return appointmentService.getAppointmentsByDoctor(id);
    }

    // =========================
    // SEARCH DOCTORS BY SPECIALIZATION
    // =========================
    @GetMapping("/specialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(
            @PathVariable String specialization) {

        return doctorService.getDoctorsBySpecialization(specialization);
    }
    

    // =========================
    // PAGINATION
    // =========================
    @GetMapping("/page")
    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorService.getAllDoctors(pageable);
    }
    @GetMapping("/search")
    public Page<Doctor> searchDoctors(
            @RequestParam(required = false) String specialization,
            Pageable pageable
    ) {
        return doctorService.searchDoctorsWithPagination(
                specialization,
                pageable
        );
    }
}