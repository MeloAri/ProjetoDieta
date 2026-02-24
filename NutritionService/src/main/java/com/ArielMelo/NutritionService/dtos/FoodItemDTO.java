package com.ArielMelo.NutritionService.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FoodItemDTO {

    @NotBlank
    private String name;

    @Positive
    private double portionGrams;
}
