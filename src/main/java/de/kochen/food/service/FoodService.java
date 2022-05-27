package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NoContentException;
import de.kochen.food.util.NotFoundException;

public interface FoodService {

	/**
	 * @param foodDto neues anzulegendes Food
	 * @return neu angelegtes Food
	 * @throws IdNotAllowedException id is not allowed
	 * @throws NotFoundException     unit is not found
	 * @throws FoundException        food already exists
	 */
	FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException;

	/**
	 * @param foodDto food Dto to change
	 * @return changed food Dto
	 * @throws NotFoundException data not found
	 */
	FoodDto putFood(FoodDto foodDto) throws NotFoundException;

	/**
	 * @param foodId food to delete
	 * @throws NoContentException content to delete not found
	 */
	void deleteFood(Long foodId) throws NoContentException;
}
