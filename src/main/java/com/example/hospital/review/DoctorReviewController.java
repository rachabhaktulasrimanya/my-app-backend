package com.example.hospital.review;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class DoctorReviewController {

    private final DoctorReviewService service;

    public DoctorReviewController(DoctorReviewService service) {
        this.service = service;
    }

    @PostMapping
    public DoctorReview addReview(@RequestBody DoctorReview review) {
        return service.addReview(review);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorReview> getDoctorReviews(@PathVariable Long doctorId) {
        return service.getDoctorReviews(doctorId);
    }
    @GetMapping("/top-doctors")
    public List<DoctorReviewStatsDTO> getTopDoctors(){
        return service.getTopDoctors();
    }
}