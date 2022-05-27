package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.util.NotFoundException;

import java.util.List;

public interface FoodGetService {
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
}