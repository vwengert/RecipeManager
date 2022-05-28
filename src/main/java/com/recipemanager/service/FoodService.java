package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;

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
