package com.example.hospital.appointment;

import com.example.hospital.doctor.Doctor;
import com.example.hospital.doctor.DoctorRepository;
import com.example.hospital.patient.Patient;
import com.example.hospital.patient.PatientRepository;
import com.example.hospital.schedule.Schedule;
import com.example.hospital.schedule.ScheduleRepository;
import com.example.hospital.sms.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;
    private final SmsService smsService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              ScheduleRepository scheduleRepository,
                              SmsService smsService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.smsService = smsService;
    }

	 // =========================
	 // GET ALL APPOINTMENTS
	 // =========================
	 public List<Appointment> getAllAppointments() {
	     return appointmentRepository.findAll();
	 }

    // =========================
    // BOOK APPOINTMENT
    // =========================
    public Appointment bookAppointment(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found with id: "
                                + request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: "
                                + request.getDoctorId()));

        // =========================
        // CHECK DOCTOR SCHEDULE
        // =========================
        DayOfWeek day = request.getDate().getDayOfWeek();

        Optional<Schedule> scheduleOpt =
                scheduleRepository.findByDoctorIdAndDayOfWeek(
                        request.getDoctorId(),
                        day
                );

        if (scheduleOpt.isPresent()) {

            Schedule schedule = scheduleOpt.get();

            if (request.getTime().isBefore(schedule.getStartTime())
                    || request.getTime().isAfter(schedule.getEndTime())) {

                throw new RuntimeException(
                        "Appointment time is outside doctor schedule"
                );
            }
        }

        // =========================
        // CHECK DOUBLE BOOKING
        // =========================
        boolean alreadyBooked =
                appointmentRepository.existsByDoctorIdAndDateAndTime(
                        request.getDoctorId(),
                        request.getDate(),
                        request.getTime()
                );

        if (alreadyBooked) {
            throw new RuntimeException("This time slot is already booked for the doctor.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());

        // Default appointment status
        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);

        String message =
                "Hello " + patient.getName() +
                ", your appointment with Dr. " + doctor.getName() +
                " is booked on " + saved.getDate() +
                " at " + saved.getTime() +
                ". Status: PENDING.";

        if (patient.getPhone() != null && !patient.getPhone().isEmpty()) {
            smsService.sendSms(patient.getPhone(), message);
        }

        return saved;
    }

    // =========================
    // GET APPOINTMENTS BY DOCTOR
    // =========================
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // =========================
    // GET APPOINTMENTS BY PATIENT
    // =========================
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // =========================
    // UPDATE APPOINTMENT
    // =========================
    public Appointment updateAppointment(Long appointmentId, AppointmentRequest request) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found with id: " + appointmentId));

        boolean alreadyBooked =
                appointmentRepository.existsByDoctorIdAndDateAndTime(
                        request.getDoctorId(),
                        request.getDate(),
                        request.getTime()
                );

        if (alreadyBooked) {
            throw new RuntimeException("This time slot is already booked for the doctor.");
        }

        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());

        return appointmentRepository.save(appointment);
    }

    // =========================
    // CANCEL APPOINTMENT
    // =========================
    public Appointment cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);

        return appointmentRepository.save(appointment);
    }

    // =========================
    // COMPLETE APPOINTMENT
    // =========================
    public Appointment completeAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.COMPLETED);

        return appointmentRepository.save(appointment);
    }

    // =========================
    // DOCTOR APPROVE APPOINTMENT
    // =========================
    public Appointment approveAppointment(Long appointmentId, Long doctorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctorId)) {
            throw new RuntimeException("You can approve only your appointments");
        }

        appointment.setStatus(AppointmentStatus.APPROVED);

        Appointment saved = appointmentRepository.save(appointment);

        Patient patient = saved.getPatient();
        Doctor doctor = saved.getDoctor();

        String message =
                "Hello " + patient.getName() +
                ", your appointment with Dr. " + doctor.getName() +
                " on " + saved.getDate() +
                " at " + saved.getTime() +
                " has been APPROVED.";

        if (patient.getPhone() != null && !patient.getPhone().isEmpty()) {
            smsService.sendSms(patient.getPhone(), message);
        }

        return saved;
    }

    // =========================
    // DOCTOR REJECT APPOINTMENT
    // =========================
    public Appointment rejectAppointment(Long appointmentId, Long doctorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctorId)) {
            throw new RuntimeException("You can reject only your appointments");
        }

        appointment.setStatus(AppointmentStatus.REJECTED);

        Appointment saved = appointmentRepository.save(appointment);

        Patient patient = saved.getPatient();
        Doctor doctor = saved.getDoctor();

        String message =
                "Hello " + patient.getName() +
                ", your appointment with Dr. " + doctor.getName() +
                " on " + saved.getDate() +
                " at " + saved.getTime() +
                " has been REJECTED.";

        if (patient.getPhone() != null && !patient.getPhone().isEmpty()) {
            smsService.sendSms(patient.getPhone(), message);
        }

        return saved;
    }

    // =========================
    // FILTER BY STATUS
    // =========================
    public List<Appointment> getByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getByDoctorAndStatus(Long doctorId,
                                                  AppointmentStatus status) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, status);
    }

    public List<Appointment> getByPatientAndStatus(Long patientId,
                                                   AppointmentStatus status) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, status);
    }

    public Page<Appointment> getAppointmentsPage(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

}