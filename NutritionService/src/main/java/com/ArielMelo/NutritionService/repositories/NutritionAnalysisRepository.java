package com.ArielMelo.NutritionService.repositories;

import com.ArielMelo.NutritionService.entities.NutritionAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NutritionAnalysisRepository
        extends JpaRepository<NutritionAnalysis, String> {

    List<NutritionAnalysis> findByUserId(String userId);

    List<NutritionAnalysis> findByUserIdAndCreatedAtBetween(
            String userId,
            LocalDateTime start,
            LocalDateTime end
    );
}
