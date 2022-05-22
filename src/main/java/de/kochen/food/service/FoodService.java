package de.kochen.food.service;

import de.kochen.food.model.Food;
import de.kochen.food.util.NotFoundException;

public interface FoodService {
    Food getFoodById(Long foodId) throws NotFoundException;
}
