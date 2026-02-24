package com.ArielMelo.NutritionService.service;

import com.ArielMelo.NutritionService.dtos.DailySummaryResponseDTO;
import com.ArielMelo.NutritionService.dtos.FoodItemDTO;
import com.ArielMelo.NutritionService.dtos.NutritionResponseDTO;
import com.ArielMelo.NutritionService.entities.NutritionAnalysis;
import com.ArielMelo.NutritionService.repositories.NutritionAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NutritionCalculatorService {

    private final NutritionAnalysisRepository repository;

    private static final Map<String, Double> CALORIES_TABLE = new HashMap<>();

    static {
        CALORIES_TABLE.put("rice", 130.0);
        CALORIES_TABLE.put("chicken", 165.0);
        CALORIES_TABLE.put("beans", 76.0);
        CALORIES_TABLE.put("pasta", 131.0);
        CALORIES_TABLE.put("beef", 250.0);
    }

    // 🔹 CALCULAR E SALVAR
    public NutritionResponseDTO calculate(List<FoodItemDTO> foods) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        var items = foods.stream()
                .map(this::calculateItem)
                .collect(Collectors.toList());

        double totalCalories = items.stream()
                .mapToDouble(NutritionResponseDTO.ItemResult::getCalories)
                .sum();

        NutritionAnalysis analysis = NutritionAnalysis.builder()
                .userId(userId)
                .totalCalories(totalCalories)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(analysis);

        return new NutritionResponseDTO(totalCalories, items);
    }

    // 🔹 TOTAL DO DIA
    public double getDailyTotal() {

        String userId = getAuthenticatedUserId();

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        List<NutritionAnalysis> analyses =
                repository.findByUserIdAndCreatedAtBetween(userId, start, end);

        return analyses.stream()
                .mapToDouble(NutritionAnalysis::getTotalCalories)
                .sum();
    }

    // 🔹 RESUMO COMPLETO DO DIA
    public DailySummaryResponseDTO getDailySummary() {

        String userId = getAuthenticatedUserId();

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        List<NutritionAnalysis> analyses =
                repository.findByUserIdAndCreatedAtBetween(userId, start, end);

        double total = analyses.stream()
                .mapToDouble(NutritionAnalysis::getTotalCalories)
                .sum();

        List<DailySummaryResponseDTO.AnalysisItem> items =
                analyses.stream()
                        .map(a -> new DailySummaryResponseDTO.AnalysisItem(
                                a.getId(),
                                a.getTotalCalories(),
                                a.getCreatedAt()
                        ))
                        .toList();

        return DailySummaryResponseDTO.builder()
                .date(today)
                .totalCalories(total)
                .analysisCount(analyses.size())
                .analyses(items)
                .build();
    }

    // 🔹 MÉTODO PRIVADO PARA PEGAR USUÁRIO
    private String getAuthenticatedUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    private NutritionResponseDTO.ItemResult calculateItem(FoodItemDTO food) {

        String foodName = food.getName().toLowerCase();

        double caloriesPer100g =
                CALORIES_TABLE.getOrDefault(foodName, 0.0);

        double calculatedCalories =
                (food.getPortionGrams() / 100.0) * caloriesPer100g;

        return new NutritionResponseDTO.ItemResult(
                food.getName(),
                calculatedCalories
        );
    }
}
