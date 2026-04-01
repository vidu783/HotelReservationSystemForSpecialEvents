package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingReservation booking;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "feedback_text", nullable = false)
    private String feedbackText;

    @Column(name = "rating")
    private Integer rating; // 1-5 rating

    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "given_to")
    private User givenTo;

    // Getters and Setters
    public Integer getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Integer feedbackId) { this.feedbackId = feedbackId; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public BookingReservation getBooking() { return booking; }
    public void setBooking(BookingReservation booking) { this.booking = booking; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDateTime getFeedbackDate() { return feedbackDate; }
    public void setFeedbackDate(LocalDateTime feedbackDate) { this.feedbackDate = feedbackDate; }

    public User getGivenTo() { return givenTo; }
    public void setGivenTo(User givenTo) { this.givenTo = givenTo; }
}
