package com.example.hospital.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value; // Added this import
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct; // Added this import

@Service
public class SmsService {

    @Value("${account.sid}")
    private String ACCOUNT_SID;

    @Value("${auth.token}")
    private String AUTH_TOKEN;

    @Value("${from.phone}")
    private String FROM_PHONE;

    // Use this instead of a constructor
    @PostConstruct
    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String to, String text) {
        if (!to.startsWith("+")) {
            to = "+91" + to;
        }

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_PHONE),
                text
        ).create();
    }
}