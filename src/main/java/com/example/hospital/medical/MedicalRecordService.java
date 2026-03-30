package com.example.hospital.medical;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    // =========================
    // EXISTING: SAVE RECORD
    // =========================
    public MedicalRecord saveRecord(MedicalRecord record) {
        return repository.save(record);
    }

    // =========================
    // EXISTING: GET PATIENT RECORDS
    // =========================
    public List<MedicalRecord> getPatientRecords(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    // ====================================================
    // NEW FEATURE: GET RECORD BY ID (for download)
    // ====================================================
    public MedicalRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));
    }

    // ====================================================
    // NEW FEATURE: DELETE RECORD
    // ====================================================
    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }

    // ====================================================
    // NEW FEATURE: ADMIN GET ALL RECORDS
    // ====================================================
    public List<MedicalRecord> getAllRecords() {
        return repository.findAll();
    }
}