package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NotFoundException;

import java.util.List;

public interface FoodService {

	/**
	 * @param foodId Id to search for food
	 * @return Food with Baseunit
	 * @throws NotFoundException Food not found
	 */
	FoodDto getFoodById(Long foodId) throws NotFoundException;

	/**
	 * @return Array of Food
	 */
	List<FoodDto> getFood();

	/**
	 * @param name of Food to search
	 * @return Food if found
	 * @throws NotFoundException Food not found
	 */
	FoodDto getFoodByName(String name) throws NotFoundException;

	/**
	 * @param foodDto neues anzulegendes Food
	 * @return neu angelegtes Food
	 * @throws IdNotAllowedException id is not allowed
	 * @throws NotFoundException unit is not found
	 * @throws FoundException food already exists
	 */
	FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException;

	FoodDto putFood(FoodDto foodDto) throws NotFoundException;
}
