package com.ArielMelo.NutritionService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NutritionResponseDTO {

    private double totalCalories;
    private List<ItemResult> items;

    @Data
    @AllArgsConstructor
    public static class ItemResult {
        private String name;
        private double calories;
    }
}