package com.ArielMelo.NutritionService.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
public class NutritionRequestDTO {

    @NotEmpty(message = "Food list cannot be empty")
    @Valid
    private List<FoodItemDTO> foods;
}
