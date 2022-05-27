package de.kochen.food.service;

import de.kochen.food.model.Food;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NoContentException;
import de.kochen.food.util.NotFoundException;

public interface FoodService {

	/**
	 * @param food neues anzulegendes Food
	 * @return neu angelegtes Food
	 * @throws IdNotAllowedException id is not allowed
	 * @throws NotFoundException     unit is not found
	 * @throws FoundException        food already exists
	 */
	Food postFood(Food food) throws IdNotAllowedException, NotFoundException, FoundException;

	/**
	 * @param food food Dto to change
	 * @return changed food Dto
	 * @throws NotFoundException data not found
	 */
	Food putFood(Food food) throws NotFoundException;

	/**
	 * @param foodId food to delete
	 * @throws NoContentException content to delete not found
	 */
	void deleteFood(Long foodId) throws NoContentException;
}
