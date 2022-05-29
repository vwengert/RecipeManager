package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService, FoodGetService {
	private final FoodRepository foodRepository;
	private final UnitRepository unitRepository;

	@Override
	public Food getFoodById(Long foodId) throws NotFoundException {
		return foodRepository.findById(foodId).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<Food> getFood() {
		return foodRepository.findAll();
	}

	@Override
	public Food getFoodByName(String name) throws NotFoundException {
		return foodRepository.findByName(name).orElseThrow(NotFoundException::new);
	}

	@Override
	public Food postFood(Food food) throws IdNotAllowedException, NotFoundException, FoundException {
		if (food.getId() != null || food.getUnit().getId() != null)
			throw new IdNotAllowedException();

		if (foodRepository.existsByName(food.getName()))
			throw new FoundException();

		Unit unit = unitRepository.findByName(food.getUnit().getName()).orElseThrow(NotFoundException::new);
		food.setUnit(unit);

		return foodRepository.save(food);
	}

	@Override
	public Food putFood(Food foodRequest) throws NotFoundException {
		Food food = foodRepository.findById(foodRequest.getId()).orElseThrow(
				NotFoundException::new);

		food.setName(foodRequest.getName());
		Unit unit = unitRepository.findByName(foodRequest.getUnit().getName()).orElseThrow(
				NotFoundException::new);

		food.setUnit(unit);

		return foodRepository.save(food);
	}

	@Override
	public void deleteFood(Long foodId) throws NoContentException {
		Food food = foodRepository.findById(foodId).orElseThrow(
				NoContentException::new);

		foodRepository.delete(food);
	}

}
