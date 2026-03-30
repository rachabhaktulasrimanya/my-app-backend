package com.example.hospital.schedule;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "*")
public class DoctorScheduleController {

    private final DoctorScheduleService service;

    public DoctorScheduleController(DoctorScheduleService service) {
        this.service = service;
    }

    // Add schedule
    @PostMapping("/add")
    public DoctorSchedule addSchedule(@RequestBody DoctorSchedule schedule) {
        return service.addSchedule(schedule);
    }

    // Get schedules of doctor
    @GetMapping("/doctor/{doctorId}")
    public List<DoctorSchedule> getSchedule(@PathVariable Long doctorId) {
        return service.getDoctorSchedule(doctorId);
    }

    // Update schedule
    @PutMapping("/{id}")
    public DoctorSchedule updateSchedule(
            @PathVariable Long id,
            @RequestBody DoctorSchedule schedule) {

        return service.updateSchedule(id, schedule);
    }

    // Delete schedule
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        service.deleteSchedule(id);
    }
}