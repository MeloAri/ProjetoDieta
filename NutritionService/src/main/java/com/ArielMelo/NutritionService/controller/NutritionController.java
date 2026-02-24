package com.ArielMelo.NutritionService.controller;

import com.ArielMelo.NutritionService.dtos.DailySummaryResponseDTO;
import com.ArielMelo.NutritionService.dtos.NutritionRequestDTO;
import com.ArielMelo.NutritionService.dtos.NutritionResponseDTO;
import com.ArielMelo.NutritionService.service.NutritionCalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/nutrition", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionCalculatorService service;

    // 🔹 1️⃣ Calcular e salvar análise
    @PostMapping(
            value = "/calculate",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<NutritionResponseDTO> calculate(
            @RequestBody @Valid NutritionRequestDTO request) {

        NutritionResponseDTO response =
                service.calculate(request.getFoods());

        return ResponseEntity.ok(response);
    }

    // 🔹 2️⃣ Retornar total de calorias do dia
    @GetMapping("/daily-total")
    public ResponseEntity<Double> getDailyTotal() {

        double total = service.getDailyTotal();

        return ResponseEntity.ok(total);
    }

    // 🔹 3️⃣ Retornar resumo completo do dia
    @GetMapping("/daily-summary")
    public ResponseEntity<DailySummaryResponseDTO> getDailySummary() {

        DailySummaryResponseDTO response =
                service.getDailySummary();

        return ResponseEntity.ok(response);
    }
}
