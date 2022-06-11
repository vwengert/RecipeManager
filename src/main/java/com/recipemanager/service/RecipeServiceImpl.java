package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
	private final RecipeRepository recipeRepository;
	private final RecipeHeaderRepository recipeHeaderRepository;
	private final FoodRepository foodRepository;

	@Override
	public List<Recipe> getRecipeByRecipeHeaderId(Long recipeHeaderId) throws NotFoundException {
		List<Recipe> recipeList = recipeRepository.findByRecipeHeaderId(recipeHeaderId);
		if (recipeList.isEmpty()) {
			throw new NotFoundException();
		}
		return recipeList;
	}

	@Override
	public Recipe putRecipe(Recipe recipe) {
		return null;
	}

	@Override
	public Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, NotFoundException {
		if (recipe.getId() != null)
			throw new IdNotAllowedException();
		if (!recipeHeaderRepository.existsById(recipe.getRecipeHeader().getId()))
			throw new NotFoundException();
		if (!foodRepository.existsById(recipe.getFood().getId()))
			throw new NotFoundException();

		return recipeRepository.save(recipe);
	}

}
