package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;

@Entity
@Table(name = "WebsiteDashboard")
public class WebsiteDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private Integer dashboardId;

    @Column(name = "page_type")
    private String pageType;

    @Column(name = "display_order")
    private Integer displayOrder;

    // Getters and Setters
    public Integer getDashboardId() { return dashboardId; }
    public void setDashboardId(Integer dashboardId) { this.dashboardId = dashboardId; }

    public String getPageType() { return pageType; }
    public void setPageType(String pageType) { this.pageType = pageType; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
