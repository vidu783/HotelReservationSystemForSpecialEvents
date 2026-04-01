package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking_Reservation")
public class BookingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name="guest_id", nullable=false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name="hall_id")
    private Hall hall;

    @Column(name="num_of_guests")
    private Integer numOfGuests;

    @Column(name="booking_date", nullable=false)
    private LocalDateTime bookingDate;

    @Column(name="status")
    private String status; // Pending, Confirmed, Rejected, InfoRequired

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Getters and Setters
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public Integer getNumOfGuests() { return numOfGuests; }
    public void setNumOfGuests(Integer numOfGuests) { this.numOfGuests = numOfGuests; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}
