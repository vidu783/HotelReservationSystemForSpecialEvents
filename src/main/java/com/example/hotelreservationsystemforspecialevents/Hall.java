package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;

@Entity
@Table(name = "Halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Integer hallId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    // Getters and Setters
    public Integer getHallId() { return hallId; }
    public void setHallId(Integer hallId) { this.hallId = hallId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
