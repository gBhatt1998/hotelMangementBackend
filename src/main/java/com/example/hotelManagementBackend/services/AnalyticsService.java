package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RevenueAggregationDTO;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private ReservationRepository reservationRepository;

    @PostConstruct
    public List<RevenueAggregationDTO> getMonthlyRevenue() {
        List<Object[]> rawResults = reservationRepository.getMonthlyRevenue();
        List<RevenueAggregationDTO> finalResults = new ArrayList<>();
        System.out.println("monthly report: " + rawResults);

        for (Object[] row : rawResults) {
            String period = (String) row[0];
            System.out.println("monthly period: " + period);

            double revenue = row[1] instanceof BigDecimal ?
                    ((BigDecimal) row[1]).doubleValue() :
                    Double.parseDouble(row[1].toString());

            System.out.println("monthly revenue: " + revenue);

            RevenueAggregationDTO dto = new RevenueAggregationDTO();
            dto.setPeriod(period);
            dto.setTotalRevenue(revenue);

            finalResults.add(dto);
        }

        return finalResults;
    }

    public List<RevenueAggregationDTO> getWeeklyRevenue() {
        List<Object[]> rawResults = reservationRepository.getWeeklyRevenue(); // e.g., 202528
        List<RevenueAggregationDTO> finalResults = new ArrayList<>();

        // Manual month name map
        Map<Integer, String> monthMap = new HashMap<>();
        monthMap.put(1, "January");
        monthMap.put(2, "February");
        monthMap.put(3, "March");
        monthMap.put(4, "April");
        monthMap.put(5, "May");
        monthMap.put(6, "June");
        monthMap.put(7, "July");
        monthMap.put(8, "August");
        monthMap.put(9, "September");
        monthMap.put(10, "October");
        monthMap.put(11, "November");
        monthMap.put(12, "December");

        for (Object[] row : rawResults) {
            int yearWeek = Integer.parseInt(row[0].toString()); // e.g., 202528
            double revenue = row[1] instanceof BigDecimal ?
                    ((BigDecimal) row[1]).doubleValue() :
                    Double.parseDouble(row[1].toString());

            int year = yearWeek / 100;
            int weekOfYear = yearWeek % 100;

            // Calculate start of week
            LocalDate jan1 = LocalDate.of(year, 1, 1);
            LocalDate startOfWeek = jan1.plusWeeks(weekOfYear - 1).with(DayOfWeek.MONDAY);

            int month = startOfWeek.getMonthValue();
            int day = startOfWeek.getDayOfMonth();
            int weekOfMonth = ((day - 1) / 7) + 1;

            String monthName = monthMap.get(month);
            String label = "Week " + weekOfMonth + " of " + monthName + " " + year;

            RevenueAggregationDTO dto = new RevenueAggregationDTO();
            dto.setPeriod(label);
            dto.setTotalRevenue(revenue);

            finalResults.add(dto);
        }

        return finalResults;
    }
}
