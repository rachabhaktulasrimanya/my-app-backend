package com.example.hospital.admin;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // =========================
    // ADMIN DASHBOARD
    // =========================
    @GetMapping("/stats")
    public Map<String, Object> stats() {

        return adminService.getDashboardStats();
    }
}