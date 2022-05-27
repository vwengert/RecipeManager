package de.kochen.food.service;

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

		if (foodRepository.findByName(food.getName()).isPresent())
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
