package de.kochen.food.controller;

import de.kochen.food.model.Food;
import de.kochen.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/food")
public class FoodController {
    private final FoodService foodService;


    @GetMapping(path = "{foodId}")
    public Food getFoodById(@PathVariable Long foodId) {
        return foodService.getFoodById(foodId);
    }


}
