package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable=false)
    private Event event;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @Column(name="priority")
    private String priority;

    @Column(name="due_time")
    private LocalDateTime dueTime;

    @ManyToOne
    @JoinColumn(name="assigned_by")
    private User assignedBy;

    @ManyToOne
    @JoinColumn(name="assign_to")
    private User assignTo;

    @ManyToOne
    @JoinColumn(name="approved_by")
    private User approvedBy;

    // Getters and Setters
    public Integer getTaskId() { return taskId; }
    public void setTaskId(Integer taskId) { this.taskId = taskId; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDateTime getDueTime() { return dueTime; }
    public void setDueTime(LocalDateTime dueTime) { this.dueTime = dueTime; }

    public User getAssignedBy() { return assignedBy; }
    public void setAssignedBy(User assignedBy) { this.assignedBy = assignedBy; }

    public User getAssignTo() { return assignTo; }
    public void setAssignTo(User assignTo) { this.assignTo = assignTo; }

    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
}
