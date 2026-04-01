package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Integer offerId;

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "status")
    private String status;

    @Column(name = "display_start")
    private LocalDate displayStart;

    @Column(name = "display_end")
    private LocalDate displayEnd;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    // Getters and Setters
    public Integer getOfferId() { return offerId; }
    public void setOfferId(Integer offerId) { this.offerId = offerId; }

    public String getOfferName() { return offerName; }
    public void setOfferName(String offerName) { this.offerName = offerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDisplayStart() { return displayStart; }
    public void setDisplayStart(LocalDate displayStart) { this.displayStart = displayStart; }

    public LocalDate getDisplayEnd() { return displayEnd; }
    public void setDisplayEnd(LocalDate displayEnd) { this.displayEnd = displayEnd; }

    public Campaign getCampaign() { return campaign; }
    public void setCampaign(Campaign campaign) { this.campaign = campaign; }
}
