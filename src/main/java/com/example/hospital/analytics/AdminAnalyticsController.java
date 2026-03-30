package com.example.hospital.analytics;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/analytics")
public class AdminAnalyticsController {

    private final AdminAnalyticsService service;

    public AdminAnalyticsController(AdminAnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Long> getAnalytics(){

        Map<String, Long> data = new HashMap<>();

        data.put("totalAppointments", service.totalAppointments());
        data.put("completedAppointments", service.completedAppointments());
        data.put("pendingAppointments", service.pendingAppointments());
        data.put("approvedAppointments", service.approvedAppointments());
        data.put("rejectedAppointments", service.rejectedAppointments());

        data.put("totalDoctors", service.totalDoctors());
        data.put("totalPatients", service.totalPatients());

        return data;
    }
}