package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;

public interface RecipeService {
	Recipe getRecipeById(Long recipeId) throws NotFoundException;
}
