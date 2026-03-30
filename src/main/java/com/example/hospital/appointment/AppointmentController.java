package com.example.hospital.appointment;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
	 // =========================
	 // GET ALL APPOINTMENTS (ADMIN)
	 // =========================
	 @GetMapping
	 public List<Appointment> getAllAppointments() {
	     return appointmentService.getAllAppointments();
	 }

    // =========================
    // BOOK APPOINTMENT
    // =========================
    @PostMapping("/book")
    public Appointment book(@RequestBody AppointmentRequest request) {
        return appointmentService.bookAppointment(request);
    }

    // =========================
    // GET BY DOCTOR
    // =========================
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> byDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    // =========================
    // GET BY PATIENT
    // =========================
    @GetMapping("/patient/{patientId}")
    public List<Appointment> byPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    // =========================
    // UPDATE APPOINTMENT
    // =========================
    @PutMapping("/update/{appointmentId}")
    public Appointment update(@PathVariable Long appointmentId,
                              @RequestBody AppointmentRequest request) {
        return appointmentService.updateAppointment(appointmentId, request);
    }

    // =========================
    // CANCEL APPOINTMENT (Soft)
    // =========================
    @PutMapping("/cancel/{appointmentId}")
    public Appointment cancel(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    // =========================
    // COMPLETE APPOINTMENT
    // =========================
    @PutMapping("/complete/{appointmentId}")
    public Appointment complete(@PathVariable Long appointmentId) {
        return appointmentService.completeAppointment(appointmentId);
    }
 // =========================
 // FILTER BY STATUS
 // =========================
 @GetMapping("/status/{status}")
 public List<Appointment> byStatus(@PathVariable AppointmentStatus status) {
     return appointmentService.getByStatus(status);
 }

 // =========================
 // FILTER BY DOCTOR + STATUS
 // =========================
 @GetMapping("/doctor/{doctorId}/status/{status}")
 public List<Appointment> byDoctorAndStatus(
         @PathVariable Long doctorId,
         @PathVariable AppointmentStatus status) {

     return appointmentService.getByDoctorAndStatus(doctorId, status);
 }

 // =========================
 // FILTER BY PATIENT + STATUS
 // =========================
 @GetMapping("/patient/{patientId}/status/{status}")
 public List<Appointment> byPatientAndStatus(
         @PathVariable Long patientId,
         @PathVariable AppointmentStatus status) {

     return appointmentService.getByPatientAndStatus(patientId, status);
 }
//=========================
//DOCTOR APPROVE
//=========================
@PutMapping("/doctor/{doctorId}/approve/{appointmentId}")
public Appointment approve(@PathVariable Long doctorId,
                         @PathVariable Long appointmentId) {

  return appointmentService.approveAppointment(appointmentId, doctorId);
}

//=========================
//DOCTOR REJECT
//=========================
@PutMapping("/doctor/{doctorId}/reject/{appointmentId}")
public Appointment reject(@PathVariable Long doctorId,
                        @PathVariable Long appointmentId) {

  return appointmentService.rejectAppointment(appointmentId, doctorId);
}
@GetMapping("/page")
public Page<Appointment> getAppointmentsPage(Pageable pageable) {
    return appointmentService.getAppointmentsPage(pageable);
}
}