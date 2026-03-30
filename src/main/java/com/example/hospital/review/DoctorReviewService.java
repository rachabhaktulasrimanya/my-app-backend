package com.example.hospital.review;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorReviewService {

    private final DoctorReviewRepository repository;

    public DoctorReviewService(DoctorReviewRepository repository) {
        this.repository = repository;
    }

    public DoctorReview addReview(DoctorReview review) {
        review.setDate(LocalDate.now());
        return repository.save(review);
    }

    public List<DoctorReview> getDoctorReviews(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public List<DoctorReviewStatsDTO> getTopDoctors(){
        return repository.findTopDoctors(PageRequest.of(0,6));
    }
}