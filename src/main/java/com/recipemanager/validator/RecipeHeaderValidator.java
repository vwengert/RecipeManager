package com.recipemanager.validator;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeHeaderValidator {
	/**
	 * @param recipeHeader recipe header to check for in repository
	 * @throws NotFoundException if recipe header not exists
	 */
	void checkRecipeHeaderExistsOrElseThrowException(RecipeHeader recipeHeader) throws NotFoundException;
}
