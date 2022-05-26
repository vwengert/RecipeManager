package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NoContentException;
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
	private final UnitRepository unitRepository;
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

	@Override
	public FoodDto getFoodByName(String name) throws NotFoundException {
		return modelMapper.map(foodRepository.findByName(name).orElseThrow(NotFoundException::new), FoodDto.class);
	}

	@Override
	public FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
		if (foodDto.getId() != null || foodDto.getUnitId() != null)
			throw new IdNotAllowedException();
		if (foodRepository.findByName(foodDto.getName()).isPresent())
			throw new FoundException();
		Unit unit = unitRepository.findByName(foodDto.getUnitName()).orElseThrow(NotFoundException::new);

		Food food = modelMapper.map(foodDto, Food.class);
		food.setUnit(unit);
		Food savedFood = foodRepository.save(food);

		return modelMapper.map(savedFood, FoodDto.class);
	}

	@Override
	public FoodDto putFood(FoodDto foodDto) throws NotFoundException {

		Food food = foodRepository.findById(foodDto.getId()).orElseThrow(
				NotFoundException::new
		);
		food.setName(foodDto.getName());
		Unit unit = unitRepository.findByName(foodDto.getUnitName()).orElseThrow(
				NotFoundException::new
		);
		food.setUnit(unit);
		Food savedFood = foodRepository.save(food);

		return modelMapper.map(savedFood, FoodDto.class);
	}

	@Override
	public void deleteFood(Long foodId) throws NoContentException {
		Food food = foodRepository.findById(foodId).orElseThrow(
				NoContentException::new
		);
		foodRepository.delete(food);
	}

}
