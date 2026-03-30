package com.example.hospital.schedule;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class Schedule {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Long doctorId;

 @Enumerated(EnumType.STRING)
 private DayOfWeek dayOfWeek;

 private LocalTime startTime;

 private LocalTime endTime;

 private Integer slotDuration;

 public Long getId() {
  return id;
 }

 public Long getDoctorId() {
  return doctorId;
 }

 public void setDoctorId(Long doctorId) {
  this.doctorId = doctorId;
 }

 public DayOfWeek getDayOfWeek() {
  return dayOfWeek;
 }

 public void setDayOfWeek(DayOfWeek dayOfWeek) {
  this.dayOfWeek = dayOfWeek;
 }

 public LocalTime getStartTime() {
  return startTime;
 }

 public void setStartTime(LocalTime startTime) {
  this.startTime = startTime;
 }

 public LocalTime getEndTime() {
  return endTime;
 }

 public void setEndTime(LocalTime endTime) {
  this.endTime = endTime;
 }

 public Integer getSlotDuration() {
  return slotDuration;
 }

 public void setSlotDuration(Integer slotDuration) {
  this.slotDuration = slotDuration;
 }

}