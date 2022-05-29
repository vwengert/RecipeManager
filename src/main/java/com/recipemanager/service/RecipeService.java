package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface RecipeService {
	/**
	 * @param recipeId id of recipe to get
	 * @return recipe
	 * @throws NotFoundException no recipe found
	 */
	Recipe getRecipeById(Long recipeId) throws NotFoundException;

	/**
	 * @return get a list of recipes
	 */
	List<Recipe> getRecipe();
}
