package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
	private final RecipeRepository recipeRepository;

	@Override
	public List<Recipe> getRecipeByRecipeHeaderId(Long recipeHeaderId) throws NotFoundException {
		List<Recipe> recipeList = recipeRepository.findByRecipeHeaderId(recipeHeaderId);
		if (recipeList.isEmpty()) {
			throw new NotFoundException();
		}
		return recipeList;
	}

}
