package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}

