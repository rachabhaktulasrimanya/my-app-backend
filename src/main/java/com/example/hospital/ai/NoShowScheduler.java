package com.example.hospital.ai;

import com.example.hospital.appointment.Appointment;
import com.example.hospital.appointment.AppointmentRepository;
import com.example.hospital.appointment.AppointmentStatus;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NoShowScheduler {

    private final AppointmentRepository appointmentRepository;
    private final NoShowPredictionService predictionService;
    private final NoShowReminderService reminderService;

    public NoShowScheduler(
            AppointmentRepository appointmentRepository,
            NoShowPredictionService predictionService,
            NoShowReminderService reminderService){

        this.appointmentRepository = appointmentRepository;
        this.predictionService = predictionService;
        this.reminderService = reminderService;
    }

    // TEST MODE → runs every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void autoReminder(){
    	System.out.println("AI Scheduler Running..");

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Appointment> list =
                appointmentRepository.findByDateAndStatus(
                        tomorrow,
                        AppointmentStatus.APPROVED
                );
        System.out.println("Appointments found="+list.size());

        for(Appointment a : list){

            System.out.println(
                    "Checking appointment: " + a.getId()
            );

            String risk =
                    predictionService.predict(
                            a.getPatient().getId()
                    );

            System.out.println(
                    "Risk for patient "
                            + a.getPatient().getId()
                            + " = " + risk
            );
            if(risk.equals("HIGH")){

                System.out.println("Reminder flag = " + a.isReminderSent());

                if(!a.isReminderSent()){

                    reminderService.sendReminder(a);

                    a.setReminderSent(true);

                    appointmentRepository.save(a);

                    System.out.println(
                            "AI Reminder sent for appointment " + a.getId()
                    );
                }
                else{
                    System.out.println(
                            "Reminder already sent for appointment " + a.getId()
                    );
                }
            }
        }
    }
}