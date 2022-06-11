package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface FoodService {
	/**
	 * @param foodId id to search for food
	 * @return Food with base unit
	 * @throws NotFoundException Food not found
	 */
	Food getFoodById(Long foodId) throws NotFoundException;

	/**
	 * @return Array of Food
	 */
	List<Food> getFood();

	/**
	 * @param name of Food to search
	 * @return Food if found
	 * @throws NotFoundException Food not found
	 */
	Food getFoodByName(String name) throws NotFoundException;

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
