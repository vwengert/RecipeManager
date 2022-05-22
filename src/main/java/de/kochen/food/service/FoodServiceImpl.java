package de.kochen.food.service;

import de.kochen.food.model.Food;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {
    @Override
    public Food getFoodById(Long foodId) {
        return new Food(1L, "Kuchen", 1L);
    }
}
