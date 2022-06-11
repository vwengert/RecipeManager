package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface RecipeService {
	List<Recipe> getRecipeByRecipeHeaderId(Long recipeId) throws NotFoundException;

	Recipe putRecipe(Recipe recipe);

	Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, NotFoundException;
}
