package com.recipemanager.validator;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeFoodValidator {
	/**
	 * @param food food to check for in repository
	 * @throws NotFoundException if food not exists
	 */
	void checkFoodExistsOrElseThrowException(Food food) throws NotFoundException;

	/**
	 * @param recipe         check the food if not null
	 * @param originalRecipe use food from original, when recipe was null
	 * @throws NotFoundException if the food not exists
	 */
	void checkNewFood(Recipe recipe, Recipe originalRecipe) throws NotFoundException;
}
