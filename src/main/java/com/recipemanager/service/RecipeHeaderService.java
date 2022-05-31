package com.recipemanager.service;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface RecipeHeaderService {
	/**
	 * @param recipeId id of recipe to get
	 * @return recipe
	 * @throws NotFoundException no recipe found
	 */
	RecipeHeader getRecipeHeaderById(Long recipeId) throws NotFoundException;

	/**
	 * @return get a list of recipes
	 */
	List<RecipeHeader> getRecipeHeader();

	/**
	 * @param recipeName recipe name to get
	 * @return recipe
	 * @throws NotFoundException no recipe found
	 */
	RecipeHeader getRecipeHeaderByName(String recipeName) throws NotFoundException;

	/**
	 * @param recipeHeader recipe to create
	 * @return recipe
	 * @throws IdNotAllowedException id isn't allowed
	 * @throws FoundException        recipe already found
	 */
	RecipeHeader postRecipeHeader(RecipeHeader recipeHeader) throws IdNotAllowedException, FoundException;

	/**
	 * @param recipeHeader recipe to change
	 * @return recipe
	 * @throws NotFoundException no recipe to change found
	 */
	RecipeHeader putRecipeHeader(RecipeHeader recipeHeader) throws NotFoundException;

	/**
	 * @param recipeId recipt do delete
	 * @throws NoContentException no content to delete
	 */
	void deleteRecipeHeader(Long recipeId) throws NoContentException;
}
