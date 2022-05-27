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
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService, FoodGetService {
	private final FoodRepository foodRepository;
	private final UnitRepository unitRepository;

	@Override
	public FoodDto getFoodById(Long foodId) throws NotFoundException {
		return FoodDto.getFoodDto(foodRepository.findById(foodId).orElseThrow(NotFoundException::new));
	}

	@Override
	public List<FoodDto> getFood() {
		List<Food> foodList = foodRepository.findAll();
		return FoodDto.getFoodDtoList(foodList);
	}

	@Override
	public FoodDto getFoodByName(String name) throws NotFoundException {
		return FoodDto.getFoodDto(foodRepository.findByName(name).orElseThrow(NotFoundException::new));
	}

	@Override
	public FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
		if (foodDto.getId() != null || foodDto.getUnitId() != null)
			throw new IdNotAllowedException();
		if (foodRepository.findByName(foodDto.getName()).isPresent())
			throw new FoundException();

		Unit unit = unitRepository.findByName(foodDto.getUnitName()).orElseThrow(NotFoundException::new);
		Food food = foodDto.getFood();
		food.setUnit(unit);

		return FoodDto.getFoodDto(foodRepository.save(food));
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

		return FoodDto.getFoodDto(foodRepository.save(food));
	}

	@Override
	public void deleteFood(Long foodId) throws NoContentException {
		Food food = foodRepository.findById(foodId).orElseThrow(
				NoContentException::new
		);
		foodRepository.delete(food);
	}

}
