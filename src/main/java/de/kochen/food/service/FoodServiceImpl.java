package de.kochen.food.service;

import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {
    @Override
    public String getFoodById(Long foodId) {
        return "Kuchen";
    }
}
