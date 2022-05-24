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
@RequestMapping(path = "/food/api/v1/")
public class FoodController {
    private final FoodService foodService;


    @GetMapping(path = "byId/{foodId}")
    public FoodDto getFoodById(@PathVariable Long foodId) throws NotFoundException {
        return foodService.getFoodById(foodId);
    }

    @GetMapping("food")
    public List<FoodDto> getFood() {
        return foodService.getFood();
    }

    @GetMapping(path ="byName/{name}")
    public FoodDto getFoodByName(@PathVariable String name) throws NotFoundException {
        return foodService.getFoodByName(name);
    }


}
