package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
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

	/**
	 * @param recipeName recipe name to get
	 * @return recipe
	 * @throws NotFoundException no recipe found
	 */
	Recipe getRecipeByName(String recipeName) throws NotFoundException;

	/**
	 * @param recipe recipe to create
	 * @return recipe
	 * @throws IdNotAllowedException id isn't allowed
	 * @throws FoundException        recipe already found
	 */
	Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, FoundException;

	/**
	 * @param recipe recipe to change
	 * @return recipe
	 * @throws NotFoundException no recipe to change found
	 */
	Recipe putRecipe(Recipe recipe) throws NotFoundException;

	/**
	 * @param recipeId recipt do delete
	 * @throws NoContentException no content to delete
	 */
	void deleteRecipe(Long recipeId) throws NoContentException;
}
