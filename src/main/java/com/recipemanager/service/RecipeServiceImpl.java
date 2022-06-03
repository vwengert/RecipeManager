package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

	@Override
	public Recipe getRecipeById(Long recipeId) throws NotFoundException {
		throw new NotFoundException();
	}

}
