package com.example.hospital.review;

public class DoctorReviewStatsDTO {

    private Long doctorId;
    private String doctorName;
    private String specialization;
    private Double avgRating;
    private Long totalReviews;

    public DoctorReviewStatsDTO(Long doctorId,
                                String doctorName,
                                String specialization,
                                Double avgRating,
                                Long totalReviews) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.avgRating = avgRating;
        this.totalReviews = totalReviews;
    }

    public Long getDoctorId() { return doctorId; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialization() { return specialization; }
    public Double getAvgRating() { return avgRating; }
    public Long getTotalReviews() { return totalReviews; }
}
