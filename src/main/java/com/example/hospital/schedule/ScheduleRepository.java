package com.example.hospital.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

 Optional<Schedule> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);

}