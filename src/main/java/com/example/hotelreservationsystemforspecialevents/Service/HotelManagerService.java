package com.example.hotelreservationsystemforspecialevents.Service;

import com.example.hotelreservationsystemforspecialevents.*;
import com.example.hotelreservationsystemforspecialevents.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelManagerService {

    @Autowired
    private BookingReservationRepository bookingRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HallRepository hallRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private FeedbackRepository feedbackRepo;

    // ===== Bookings =====
    public List<BookingReservation> getPendingBookings() {
        return bookingRepo.findByStatus("Pending");
    }

    public List<BookingReservation> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Optional<BookingReservation> getBooking(Integer bookingId) {
        return bookingRepo.findById(bookingId);
    }

    public void updateBookingStatus(Integer bookingId, String status, String note, Integer managerId) {
        bookingRepo.findById(bookingId).ifPresent(b -> {
            b.setStatus(status);
            bookingRepo.save(b);
        });
    }

    public List<BookingReservation> getUpcomingReservations() {
        LocalDateTime now = LocalDateTime.now();
        return bookingRepo.findByBookingDateAfterAndStatusIn(
                now,
                Arrays.asList("Pending", "Confirmed")
        );
    }

    public List<BookingReservation> getUpcomingReservationsBetween(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        return bookingRepo.findByBookingDateBetweenAndStatusIn(
                startInclusive,
                endExclusive,
                Arrays.asList("Pending", "Confirmed")
        );
    }

    public List<Hall> suggestAlternativeHalls(LocalDateTime bookingDate, int guests) {
        return hallRepo.findAll().stream()
                .filter(h -> h.getCapacity() >= guests && isHallAvailable(h, bookingDate))
                .collect(Collectors.toList());
    }

    private boolean isHallAvailable(Hall hall, LocalDateTime dateTime) {
        return bookingRepo.findAll().stream()
                .filter(b -> b.getHall() != null && b.getHall().getHallId().equals(hall.getHallId()))
                .noneMatch(b -> b.getBookingDate().equals(dateTime) && !"Rejected".equals(b.getStatus()));
    }

    // ===== Tasks & Staff =====
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public void assignTaskToStaff(Integer taskId, Integer staffId) {
        taskRepo.findById(taskId).ifPresent(task -> {
            userRepo.findById(staffId).ifPresent(staff -> {
                task.setAssignTo(staff);
                taskRepo.save(task);
            });
        });
    }

    public void assignStaffToBooking(Integer bookingId, Integer staffId, String roleDesc) {
        bookingRepo.findById(bookingId).ifPresent(b -> {
            userRepo.findById(staffId).ifPresent(staff -> {
                Task task = new Task();
                task.setEvent(b.getEvent());
                task.setAssignTo(staff);
                task.setAssignedBy(null);
                task.setDescription(roleDesc != null ? roleDesc : "Assigned staff");
                task.setStatus("Assigned");
                taskRepo.save(task);
            });
        });
    }

    // ===== Halls =====
    public List<Hall> getAllHalls() {
        return hallRepo.findAll();
    }

    public Map<Integer, Long> getHallBookingCounts() {
        return hallRepo.findAll().stream()
                .collect(Collectors.toMap(
                        Hall::getHallId,
                        hall -> bookingRepo.findAll().stream()
                                .filter(b -> b.getHall() != null && b.getHall().getHallId().equals(hall.getHallId()))
                                .count()
                ));
    }

    // ===== Users by Role =====
    public List<User> getStaffByRoles(List<String> roles) {
        return userRepo.findByRoleIn(roles);
    }

    // ===== Reports =====
    public Map<String, Object> buildBookingReport() {
        Map<String, Object> report = new HashMap<>();
        List<BookingReservation> all = bookingRepo.findAll();

        // If no real data, generate dummy data for demonstration
        if (all.isEmpty()) {
            report.put("totalBookings", 45);
            report.put("bookingsByStatus", Map.of(
                "Confirmed", 25L,
                "Pending", 12L,
                "Rejected", 5L,
                "InfoRequired", 3L
            ));
            report.put("bookingsByEventType", Map.of(
                "Wedding", 18L,
                "Corporate Event", 12L,
                "Birthday Party", 8L,
                "Conference", 7L
            ));
            report.put("monthlyBookings", Map.of(
                "2024-09", 15L,
                "2024-10", 18L,
                "2024-11", 12L
            ));
            report.put("averageGuestsPerBooking", 85.5);
            report.put("peakBookingDay", "Saturday");
            report.put("mostPopularHall", "Grand Ballroom");
            report.put("conversionRate", 78.5);
        } else {
            report.put("totalBookings", all.size());
            report.put("bookingsByStatus", all.stream().collect(Collectors.groupingBy(
                    b -> Optional.ofNullable(b.getStatus()).orElse("Unknown"), Collectors.counting())));
            report.put("bookingsByEventType", all.stream().filter(b -> b.getEvent() != null)
                    .collect(Collectors.groupingBy(b -> Optional.ofNullable(b.getEvent().getEventType()).orElse("Unknown"), Collectors.counting())));

            // Monthly bookings count
            Map<String, Long> monthly = all.stream().collect(Collectors.groupingBy(
                    b -> Optional.ofNullable(b.getBookingDate()).map(d -> d.getYear() + "-" + String.format("%02d", d.getMonthValue())).orElse("Unknown"),
                    Collectors.counting()));
            report.put("monthlyBookings", monthly);
            
            // Calculate additional metrics
            double avgGuests = all.stream()
                    .filter(b -> b.getNumOfGuests() != null)
                    .mapToInt(BookingReservation::getNumOfGuests)
                    .average().orElse(0.0);
            report.put("averageGuestsPerBooking", avgGuests);
        }

        return report;
    }

    public Map<String, Object> buildRevenueReport() {
        Map<String, Object> report = new HashMap<>();
        var payments = paymentRepo.findAll();

        // If no real data, generate dummy data for demonstration
        if (payments.isEmpty()) {
            report.put("totalRevenue", new java.math.BigDecimal("125,750.00"));
            report.put("revenueByMethod", Map.of(
                "Credit Card", new java.math.BigDecimal("75,450.00"),
                "Bank Transfer", new java.math.BigDecimal("35,200.00"),
                "Cash", new java.math.BigDecimal("15,100.00")
            ));
            report.put("revenueByStatus", Map.of(
                "Completed", new java.math.BigDecimal("98,500.00"),
                "Pending", new java.math.BigDecimal("22,250.00"),
                "Failed", new java.math.BigDecimal("5,000.00")
            ));
            report.put("monthlyRevenue", Map.of(
                "2024-09", new java.math.BigDecimal("42,500.00"),
                "2024-10", new java.math.BigDecimal("48,750.00"),
                "2024-11", new java.math.BigDecimal("34,500.00")
            ));
            report.put("averageTransactionAmount", new java.math.BigDecimal("2,795.00"));
            report.put("revenueGrowth", 15.8);
            report.put("topRevenueHall", "Grand Ballroom");
            report.put("revenuePerGuest", new java.math.BigDecimal("32.95"));
        } else {
            report.put("totalRevenue", payments.stream()
                    .map(Payment::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

            report.put("revenueByMethod", payments.stream().collect(Collectors.groupingBy(
                    p -> Optional.ofNullable(p.getMethod()).orElse("Unknown"),
                    Collectors.mapping(Payment::getAmount, Collectors.reducing(java.math.BigDecimal.ZERO, (a, b) -> a.add(Optional.ofNullable(b).orElse(java.math.BigDecimal.ZERO))))
            )));

            report.put("revenueByStatus", payments.stream().collect(Collectors.groupingBy(
                    p -> Optional.ofNullable(p.getPaymentStatus()).orElse("Unknown"),
                    Collectors.mapping(Payment::getAmount, Collectors.reducing(java.math.BigDecimal.ZERO, (a, b) -> a.add(Optional.ofNullable(b).orElse(java.math.BigDecimal.ZERO))))
            )));

            Map<String, java.math.BigDecimal> monthly = payments.stream().collect(Collectors.groupingBy(
                    p -> Optional.ofNullable(p.getPaymentDate()).map(d -> d.getYear() + "-" + String.format("%02d", d.getMonthValue())).orElse("Unknown"),
                    Collectors.mapping(Payment::getAmount, Collectors.reducing(java.math.BigDecimal.ZERO, (a, b) -> a.add(Optional.ofNullable(b).orElse(java.math.BigDecimal.ZERO))))
            ));
            report.put("monthlyRevenue", monthly);
        }

        return report;
    }

    // ===== Feedback =====
    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }

    public Map<String, Object> buildFeedbackAnalysis() {
        Map<String, Object> analysis = new HashMap<>();
        List<Feedback> all = feedbackRepo.findAll();

        // Average rating
        double avg = all.stream().map(Feedback::getRating).filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(0.0);
        analysis.put("averageRating", avg);

        // Distribution
        Map<Integer, Long> dist = all.stream().map(Feedback::getRating).filter(Objects::nonNull)
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()));
        analysis.put("ratingDistribution", dist);

        // Feedback by event type
        Map<String, Long> byType = all.stream().filter(f -> f.getEvent() != null)
                .collect(Collectors.groupingBy(f -> Optional.ofNullable(f.getEvent().getEventType()).orElse("Unknown"), Collectors.counting()));
        analysis.put("feedbackByEventType", byType);

        // Recent (last 30 days)
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        List<Feedback> recent = all.stream()
                .filter(f -> f.getFeedbackDate() != null && f.getFeedbackDate().isAfter(cutoff))
                .collect(Collectors.toList());
        analysis.put("recentFeedback", recent);

        return analysis;
    }

    // ===== Hall Availability =====
    public Map<Integer, HallAvailabilityInfo> getHallAvailability() {
        Map<Integer, HallAvailabilityInfo> availability = new HashMap<>();
        List<Hall> halls = hallRepo.findAll();
        
        for (Hall hall : halls) {
            HallAvailabilityInfo info = new HallAvailabilityInfo();
            info.setHall(hall);
            
            // Get all bookings for this hall
            List<BookingReservation> hallBookings = bookingRepo.findAll().stream()
                    .filter(b -> b.getHall() != null && b.getHall().getHallId().equals(hall.getHallId()))
                    .filter(b -> !"Rejected".equals(b.getStatus()))
                    .collect(Collectors.toList());
            
            info.setBookings(hallBookings);
            info.setAvailable(hall.getAvailability() && hallBookings.isEmpty());
            
            availability.put(hall.getHallId(), info);
        }
        
        return availability;
    }

    // Inner class for hall availability information
    public static class HallAvailabilityInfo {
        private Hall hall;
        private List<BookingReservation> bookings;
        private boolean isAvailable;

        public Hall getHall() { return hall; }
        public void setHall(Hall hall) { this.hall = hall; }

        public List<BookingReservation> getBookings() { return bookings; }
        public void setBookings(List<BookingReservation> bookings) { this.bookings = bookings; }

        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
    }
}
