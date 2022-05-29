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
	public Recipe getRecipeById(Long recipeId) throws NotFoundException {
		return recipeRepository.findById(recipeId).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public List<Recipe> getRecipe() {
		return recipeRepository.findAll();
	}

	@Override
	public Recipe getRecipeByName(String recipeName) throws NotFoundException {
		return recipeRepository.findByName(recipeName).orElseThrow(
				NotFoundException::new);
	}
}
