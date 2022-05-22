package de.kochen.food.service;

import de.kochen.food.model.Food;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public Food getFoodById(Long foodId) throws NotFoundException {
        return foodRepository.findById(foodId).orElseThrow(
                NotFoundException::new
        );
    }
}
