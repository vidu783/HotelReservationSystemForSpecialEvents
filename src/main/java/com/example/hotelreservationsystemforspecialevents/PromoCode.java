package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;

@Entity
@Table(name = "PromoCodes")
public class PromoCode {

    @Id
    @Column(name = "promo_code")
    private String promoCode;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "max_usage")
    private Integer maxUsage;

    // Getters and Setters
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }

    public Campaign getCampaign() { return campaign; }
    public void setCampaign(Campaign campaign) { this.campaign = campaign; }

    public Integer getUsageCount() { return usageCount; }
    public void setUsageCount(Integer usageCount) { this.usageCount = usageCount; }

    public Integer getMaxUsage() { return maxUsage; }
    public void setMaxUsage(Integer maxUsage) { this.maxUsage = maxUsage; }
}
