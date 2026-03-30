package com.example.hospital.schedule;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository repository;

    public DoctorScheduleService(DoctorScheduleRepository repository) {
        this.repository = repository;
    }

    // ADD schedule
    public DoctorSchedule addSchedule(DoctorSchedule schedule) {
        return repository.save(schedule);
    }

    // GET schedules for doctor
    public List<DoctorSchedule> getDoctorSchedule(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    // UPDATE schedule
    public DoctorSchedule updateSchedule(Long id, DoctorSchedule schedule) {

        DoctorSchedule existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        existing.setDayOfWeek(schedule.getDayOfWeek());
        existing.setStartTime(schedule.getStartTime());
        existing.setEndTime(schedule.getEndTime());
        existing.setSlotDuration(schedule.getSlotDuration());

        return repository.save(existing);
    }

    // DELETE schedule
    public void deleteSchedule(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }

        repository.deleteById(id);
    }
}