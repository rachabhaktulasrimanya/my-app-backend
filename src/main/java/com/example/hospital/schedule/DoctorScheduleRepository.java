package com.example.hospital.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long doctorId);

}