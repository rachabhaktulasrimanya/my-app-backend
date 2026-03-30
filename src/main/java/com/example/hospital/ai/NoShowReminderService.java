package com.example.hospital.ai;

import com.example.hospital.appointment.Appointment;
import com.example.hospital.sms.SmsService;
import org.springframework.stereotype.Service;

@Service
public class NoShowReminderService {

    private final SmsService smsService;

    public NoShowReminderService(SmsService smsService){
        this.smsService = smsService;
    }

    public void sendReminder(Appointment a){

        String phone = a.getPatient().getPhone();

        System.out.println("Sending SMS to " + phone);
        String message =
                "Reminder: You have appointment with Dr "
                + a.getDoctor().getName()
                + " on "
                + a.getDate()
                + " at "
                + a.getTime();

        smsService.sendSms(phone,message);
    }
}