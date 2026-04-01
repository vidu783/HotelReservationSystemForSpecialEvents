package com.example.hotelreservationsystemforspecialevents.Repository;

import com.example.hotelreservationsystemforspecialevents.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find by username for Spring Security login
    Optional<User> findByUsername(String username);

    // Find all users whose role is in the given list
    List<User> findByRoleIn(List<String> roles);
    List<User> findByRole(String role);

}
