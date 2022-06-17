package com.recipemanager.validator;

import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeHeaderValidator {
	/**
	 *
	 * @param recipeHeader recipe header to check for in repository
	 * @throws NotFoundException if recipe header not exists
	 */
	void checkRecipeHeaderExistsOrElseThrowException(RecipeHeader recipeHeader) throws NotFoundException;

	/**
	 *
	 * @param recipe check the recipe header if not null
	 * @param originalRecipe use recipe header from original, when recipe was null
	 * @throws NotFoundException if the recipe header not exists
	 */
	void checkNewRecipeHeader(Recipe recipe, Recipe originalRecipe) throws NotFoundException;
}
