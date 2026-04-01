package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.WebsiteDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteDashboardRepository extends JpaRepository<WebsiteDashboard, Integer> {
}
