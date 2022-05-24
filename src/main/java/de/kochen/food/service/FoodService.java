package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.NotFoundException;

import java.util.List;

public interface FoodService {

    /**
     * @param foodId Id to search for food
     * @return Food with Baseunit
     * @throws NotFoundException Food not found
     */
    FoodDto getFoodById(Long foodId) throws NotFoundException;

    List<FoodDto> getFood();

    FoodDto getFoodByName(String name) throws NotFoundException;
}
