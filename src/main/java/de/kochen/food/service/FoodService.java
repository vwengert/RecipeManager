package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.NotFoundException;

public interface FoodService {

    /**
     * @param foodId Id to search for food
     * @return Food with Baseunit
     * @throws NotFoundException Food not found
     */
    FoodDto getFoodById(Long foodId) throws NotFoundException;
}
