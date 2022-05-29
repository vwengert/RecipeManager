package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
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
	 * @param recipe changed recipe
	 * @return recipe
	 * @throws IdNotAllowedException changing id isn't allowed
	 * @throws FoundException        recipe already found
	 */
	Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, FoundException;

	Recipe putRecipe(Recipe recipe) throws NotFoundException;

}
