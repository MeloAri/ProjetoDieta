package com.ArielMelo.NutritionService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DailySummaryResponseDTO {

    private LocalDate date;
    private double totalCalories;
    private int analysisCount;
    private List<AnalysisItem> analyses;
    private double totalProtein;
    private double totalCarbs;
    private double totalFat;


    @Data
    @AllArgsConstructor
    public static class AnalysisItem {
        private String id;
        private double totalCalories;
        private LocalDateTime createdAt;
    }
}
