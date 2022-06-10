package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

import java.util.List;

public interface RecipeService {
	List<Recipe> getRecipeByRecipeHeaderId(Long recipeId) throws NotFoundException;
}
