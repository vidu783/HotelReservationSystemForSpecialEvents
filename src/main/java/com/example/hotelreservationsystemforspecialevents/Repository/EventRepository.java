package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
