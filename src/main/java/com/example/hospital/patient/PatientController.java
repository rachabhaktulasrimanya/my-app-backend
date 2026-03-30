package com.example.hospital.patient;

import com.example.hospital.appointment.Appointment;
import com.example.hospital.appointment.AppointmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PatientController(PatientService patientService,
                             AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    // =========================
    // GET ALL PATIENTS
    // =========================
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // =========================
    // PATIENT APPOINTMENT HISTORY
    // =========================
    @GetMapping("/{id}/history")
    public List<Appointment> getPatientHistory(@PathVariable Long id) {
        return appointmentService.getAppointmentsByPatient(id);
    }
    @GetMapping("/page")
    public Page<Patient> getPatientsPage(Pageable pageable) {
        return patientService.getPatientsPage(pageable);
    }
}