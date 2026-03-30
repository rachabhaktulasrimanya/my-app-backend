package com.example.hospital.ai;

import com.example.hospital.appointment.Appointment;
import com.example.hospital.appointment.AppointmentRepository;
import com.example.hospital.appointment.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoShowPredictionService {

    private final AppointmentRepository appointmentRepository;

    public NoShowPredictionService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public String predict(Long patientId){

        List<Appointment> history =
                appointmentRepository.findByPatientId(patientId);

        long cancelled =
                history.stream()
                        .filter(a ->
                                a.getStatus() == AppointmentStatus.CANCELLED
                                || a.getStatus() == AppointmentStatus.REJECTED
                        )
                        .count();

        long total = history.size();

        if(total == 0){
            return "LOW";
        }

        double ratio = (double) cancelled / total;

        if(ratio > 0.5){
            return "HIGH";
        }
        else if(ratio > 0.25){
            return "MEDIUM";
        }
        else{
            return "LOW";
        }
    }
}