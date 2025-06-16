package com.example.hotelManagementBackend.dto;
public class RevenueAggregationDTO {
    private String period;
    private double totalRevenue;

    public RevenueAggregationDTO() {}

    public RevenueAggregationDTO(String period, double totalRevenue) {
        this.period = period;
        this.totalRevenue = totalRevenue;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
