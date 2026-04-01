package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
