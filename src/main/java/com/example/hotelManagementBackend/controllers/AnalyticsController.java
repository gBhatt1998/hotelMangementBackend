package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.RevenueAggregationDTO;
import com.example.hotelManagementBackend.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/revenue/monthly")
    public ResponseEntity<List<RevenueAggregationDTO>> getMonthlyRevenue() {
        return ResponseEntity.ok(analyticsService.getMonthlyRevenue());
    }

    @GetMapping("/revenue/weekly")
    public ResponseEntity<List<RevenueAggregationDTO>> getWeeklyRevenue() {
        return ResponseEntity.ok(analyticsService.getWeeklyRevenue());
    }
}