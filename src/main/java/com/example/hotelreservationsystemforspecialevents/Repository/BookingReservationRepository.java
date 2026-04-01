package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.BookingReservation;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingReservationRepository extends JpaRepository<BookingReservation, Integer> {
    List<BookingReservation> findByStatus(String status);
    List<BookingReservation> findByHallHallId(Integer hallId);
    List<BookingReservation> findByBookingDateAfterAndStatusIn(LocalDateTime dateTime, List<String> statuses);
    List<BookingReservation> findByBookingDateBetweenAndStatusIn(LocalDateTime start, LocalDateTime end, List<String> statuses);
}
