package com.example.hospital.medical;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    // =========================
    // EXISTING: UPLOAD FILE
    // =========================
    @PostMapping("/upload")
    public MedicalRecord upload(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam MultipartFile file) throws IOException {

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        MedicalRecord record = new MedicalRecord();
        record.setPatientId(patientId);
        record.setDoctorId(doctorId);
        record.setFileName(file.getOriginalFilename());
        record.setFilePath(filePath);
        record.setUploadDate(LocalDate.now());

        return service.saveRecord(record);
    }

    // =========================
    // EXISTING: GET PATIENT RECORDS
    // =========================
    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getPatientRecords(@PathVariable Long patientId) {
        return service.getPatientRecords(patientId);
    }

    // ============================================================
    // NEW FEATURE 1: DOWNLOAD MEDICAL RECORD
    // ============================================================
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {

        MedicalRecord record = service.getById(id);

        Path path = Paths.get(record.getFilePath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + record.getFileName() + "\"")
                .body(resource);
    }

    // ============================================================
    // NEW FEATURE 2: DELETE MEDICAL RECORD
    // ============================================================
    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        service.deleteRecord(id);
    }

    // ============================================================
    // NEW FEATURE 3: ADMIN VIEW ALL RECORDS
    // ============================================================
    @GetMapping
    public List<MedicalRecord> getAllRecords() {
        return service.getAllRecords();
    }
}