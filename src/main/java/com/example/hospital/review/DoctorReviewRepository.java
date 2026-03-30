package com.example.hospital.review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long> {

    List<DoctorReview> findByDoctorId(Long doctorId);

    @Query("""
    		SELECT new com.example.hospital.review.DoctorReviewStatsDTO(
    		d.id,
    		d.name,
    		d.specialization,
    		AVG(r.rating),
    		COUNT(r)
    		)
    		FROM DoctorReview r, Doctor d
    		WHERE r.doctorId = d.id
    		GROUP BY d.id, d.name, d.specialization
    		ORDER BY COUNT(r) DESC
    		""")
    		List<DoctorReviewStatsDTO> findTopDoctors(Pageable pageable);

}