package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;

    @Override
    public FoodDto getFoodById(Long foodId) throws NotFoundException {
        return modelMapper.map(foodRepository.findById(foodId).orElseThrow(NotFoundException::new), FoodDto.class);
    }

    @Override
    public List<FoodDto> getFood() {
        List<Food> foodList = foodRepository.findAll();
        return foodList
                .stream()
                .map(food -> modelMapper.map(food, FoodDto.class))
                .collect(Collectors.toList());
    }
}
