package com.recipemanager.validator;

import com.recipemanager.model.Food;
import com.recipemanager.util.exceptions.NotFoundException;

public interface FoodValidator {
	/**
	 * @param food food to check for in repository
	 * @throws NotFoundException if food not exists
	 */
	void checkFoodExistsOrElseThrowException(Food food) throws NotFoundException;

}
