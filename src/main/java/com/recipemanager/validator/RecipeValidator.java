package com.recipemanager.validator;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeValidator {
	/**
	 * @param recipe         new Recipe to Check and Fix missing entries from original Recipe
	 * @param originalRecipe Recipe which needs to be changed
	 * @throws NotFoundException if the Recipe don't exist in repository
	 */
	void validateNewRecipeAndFixEmptyFields(Recipe recipe, Recipe originalRecipe) throws NotFoundException;

	/**
	 * @param recipe Recipe to check for exiting recipe header and food
	 * @throws NotFoundException if any of the checks for existing items fails
	 */
	void checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(Recipe recipe) throws NotFoundException;
}
