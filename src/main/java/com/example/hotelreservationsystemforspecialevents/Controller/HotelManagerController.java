package com.example.hotelreservationsystemforspecialevents.Controller;

import com.example.hotelreservationsystemforspecialevents.*;
import com.example.hotelreservationsystemforspecialevents.Service.HotelManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class HotelManagerController {

    @Autowired
    private HotelManagerService service;

    // ===== Booking Details =====
    @GetMapping("/bookingDetails")
    public String bookingDetails(Model model) {
        List<BookingReservation> pending = service.getPendingBookings();
        model.addAttribute("pendingBookings", pending);

        // Upcoming reservations for quick visibility
        List<BookingReservation> upcoming = service.getUpcomingReservations();
        model.addAttribute("upcomingReservations", upcoming);

        if (!pending.isEmpty()) {
            BookingReservation b = pending.get(0);
            model.addAttribute("booking", b);
            List<Hall> alternatives = service.suggestAlternativeHalls(
                    b.getBookingDate(),
                    b.getNumOfGuests() != null ? b.getNumOfGuests() : 0
            );
            model.addAttribute("alternatives", alternatives);
        }

        List<User> coordinators = service.getStaffByRoles(List.of("Event Coordinator", "Catering", "Housekeeping"));
        model.addAttribute("coordinators", coordinators);

        return "manager/bookingDetails";
    }

    // ===== Specific Booking Details =====
    @GetMapping("/bookings/{id}")
    public String viewBooking(@PathVariable Integer id, Model model) {
        BookingReservation b = service.getBooking(id).orElse(null);
        if (b == null) return "redirect:/manager/bookingDetails";

        List<Hall> alternatives = service.suggestAlternativeHalls(
                b.getBookingDate(),
                b.getNumOfGuests() != null ? b.getNumOfGuests() : 0
        );

        List<User> coordinators = service.getStaffByRoles(List.of("Event Coordinator","Catering","Housekeeping"));

        // Add all required attributes for the template
        model.addAttribute("booking", b);
        model.addAttribute("alternatives", alternatives);
        model.addAttribute("coordinators", coordinators);
        model.addAttribute("pendingBookings", service.getPendingBookings());
        model.addAttribute("upcomingReservations", service.getUpcomingReservations());
        return "manager/bookingDetails";
    }

    // ===== Approve / Reject / Request Info =====
    @PostMapping("/bookings/{id}/approve")
    public String approveBooking(@PathVariable Integer id,
                                 @RequestParam(name="managerId", required=false) Integer managerId) {
        service.updateBookingStatus(id, "Confirmed", null, managerId);
        return "redirect:/manager/bookings/" + id + "?approved=true";
    }

    @PostMapping("/bookings/{id}/reject")
    public String rejectBooking(@PathVariable Integer id,
                                @RequestParam(name="reason", required=false) String reason,
                                @RequestParam(name="managerId", required=false) Integer managerId) {
        service.updateBookingStatus(id, "Rejected", reason, managerId);
        return "redirect:/manager/bookings/" + id + "?rejected=true";
    }

    @PostMapping("/bookings/{id}/request-info")
    public String requestInfo(@PathVariable Integer id,
                              @RequestParam(name="note", required=false) String note,
                              @RequestParam(name="managerId", required=false) Integer managerId) {
        service.updateBookingStatus(id, "InfoRequired", note, managerId);
        return "redirect:/manager/bookings/" + id + "?infoRequested=true";
    }

    // ===== Assign Staff =====
    @PostMapping("/bookings/{id}/assign-staff")
    public String assignStaff(@PathVariable Integer id,
                              @RequestParam Integer staffId,
                              @RequestParam(name="roleDescription", required=false) String roleDesc) {
        service.assignStaffToBooking(id, staffId, roleDesc);
        return "redirect:/manager/bookings/" + id + "?staffAssigned=true";
    }

    // ===== Dashboard =====
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<BookingReservation> pending = service.getPendingBookings();
        Map<Integer, Long> hallStats = service.getHallBookingCounts();


        model.addAttribute("pendingBookings", pending);
        model.addAttribute("hallStats", hallStats);
        model.addAttribute("upcomingReservations", service.getUpcomingReservations());
        model.addAttribute("totalBookings", service.getAllBookings().size());
        model.addAttribute("pendingApprovals", pending.size());
        model.addAttribute("upcomingEvents", service.getUpcomingReservations().size());
        var analysis = service.buildFeedbackAnalysis();
        model.addAttribute("averageRating", analysis.getOrDefault("averageRating", 0.0));

        return "manager/dashboard";
    }

    // ===== Lists =====
    @GetMapping("/bookings/pending")
    public String listPending(Model model) {
        model.addAttribute("bookings", service.getPendingBookings());
        return "manager/pendingBookings";
    }

    @GetMapping("/reservations/upcoming")
    public String listUpcoming(Model model) {
        model.addAttribute("reservations", service.getUpcomingReservations());
        return "manager/upcomingReservations";
    }


    // ===== View Tasks =====
    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        return "manager/tasks";
    }

    // ===== Reports =====
    @GetMapping("/reports")
    public String reportsDashboard(Model model) {
        model.addAttribute("bookingReport", service.buildBookingReport());
        model.addAttribute("revenueReport", service.buildRevenueReport());
        return "manager/reportsDashboard";
    }

    @GetMapping("/reports/booking")
    public String bookingReport(Model model) {
        model.addAttribute("report", service.buildBookingReport());
        return "manager/bookingReport";
    }

    @GetMapping("/reports/revenue")
    public String revenueReport(Model model) {
        model.addAttribute("report", service.buildRevenueReport());
        return "manager/revenueReport";
    }

    // ===== Feedback =====
    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("feedback", service.getAllFeedback());
        return "manager/feedback";
    }

    @GetMapping("/feedback/analysis")
    public String feedbackAnalysis(Model model) {
        model.addAttribute("analysis", service.buildFeedbackAnalysis());
        return "manager/feedbackAnalysis";
    }

    // ===== Hall Availability =====
    @GetMapping("/halls/availability")
    public String hallAvailability(Model model) {
        model.addAttribute("hallAvailability", service.getHallAvailability());
        return "manager/hallAvailability";
    }
}
