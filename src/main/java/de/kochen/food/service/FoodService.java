package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.NotFoundException;

public interface FoodService {
    FoodDto getFoodById(Long foodId) throws NotFoundException;
}
