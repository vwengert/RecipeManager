package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;

    @Override
    public FoodDto getFoodById(UUID foodId) throws NotFoundException {
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

    @Override
    public FoodDto getFoodByName(String name) throws NotFoundException {
        return modelMapper.map(foodRepository.findByName(name).orElseThrow(NotFoundException::new), FoodDto.class);
    }

    @Override
    public FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
        if (foodDto.getGuid() != null || foodDto.getUnitGuid() != null)
            throw new IdNotAllowedException();
        if (foodRepository.findByName(foodDto.getName()).isPresent())
            throw new FoundException();
        Unit unit = unitRepository.findByName(foodDto.getUnitName()).orElseThrow(NotFoundException::new);

        Food food = modelMapper.map(foodDto, Food.class);
        food.setUnit(unit);
        Food savedFood = foodRepository.save(food);


        return modelMapper.map(savedFood, FoodDto.class);
    }
}
