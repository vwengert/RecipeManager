package com.recipemanager.validator;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeValidator {
	void validateNewRecipeAndFixEmptyFields(Recipe recipe, Recipe originalRecipe) throws NotFoundException;

	void checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(Recipe recipe) throws NotFoundException;
}
