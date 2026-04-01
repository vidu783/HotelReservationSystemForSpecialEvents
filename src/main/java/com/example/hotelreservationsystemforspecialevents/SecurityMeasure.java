package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SecurityMeasures")
public class SecurityMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_id")
    private Integer securityId;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "implementation_date")
    private LocalDate implementationDate;

    @ManyToOne
    @JoinColumn(name = "implemented_by")
    private User implementedBy;

    // Getters and Setters
    public Integer getSecurityId() { return securityId; }
    public void setSecurityId(Integer securityId) { this.securityId = securityId; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public LocalDate getImplementationDate() { return implementationDate; }
    public void setImplementationDate(LocalDate implementationDate) { this.implementationDate = implementationDate; }

    public User getImplementedBy() { return implementedBy; }
    public void setImplementedBy(User implementedBy) { this.implementedBy = implementedBy; }
}
