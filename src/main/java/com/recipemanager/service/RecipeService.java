package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface RecipeService {
	/**
	 * @param recipeId Id of Recipe to get the list
	 * @return list of items from recipe
	 * @throws NotFoundException id is not found
	 */
	List<Recipe> getRecipeByRecipeHeaderId(Long recipeId) throws NotFoundException;

	/**
	 * @param recipe item to change
	 * @return changed recipe item
	 * @throws NotFoundException recipe id not found
	 */
	Recipe putRecipe(Recipe recipe) throws NotFoundException;

	/**
	 * @param recipe item to add to recipe
	 * @return saved recipe item
	 * @throws IdNotAllowedException id not allowed to add a new item
	 * @throws NotFoundException     id of recipe header, food or unit not found
	 */
	Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, NotFoundException;
}
