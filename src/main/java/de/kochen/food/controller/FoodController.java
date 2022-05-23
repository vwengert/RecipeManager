package de.kochen.food.controller;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.service.FoodService;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/food")
public class FoodController {
    private final FoodService foodService;


    @GetMapping(path = "{foodId}")
    public FoodDto getFoodById(@PathVariable Long foodId) throws NotFoundException {
        return foodService.getFoodById(foodId);
    }

    @GetMapping()
    public List<FoodDto> getFood() {
        return List.of(
                FoodDto.builder().name("Kuchen").build(),
                FoodDto.builder().name("Kartoffeln").build()
        );
    }


}
