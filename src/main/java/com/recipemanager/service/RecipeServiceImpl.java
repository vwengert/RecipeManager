package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
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

	@Override
	public Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, FoundException {
		if (recipe.getId() != null)
			throw new IdNotAllowedException();
		if (recipeRepository.existsByName(recipe.getName()))
			throw new FoundException();
		return recipeRepository.save(recipe);
	}

	@Override
	public Recipe putRecipe(Recipe recipe) throws NotFoundException {
		Recipe oldRecipe = recipeRepository.findById(recipe.getId()).orElseThrow(
				NotFoundException::new
		);
		oldRecipe.setName(recipe.getName());
		oldRecipe.setDescription(recipe.getDescription());
		oldRecipe.setPortions(recipe.getPortions());

		return recipeRepository.save(oldRecipe);
	}

	@Override
	public void deleteRecipe(Long recipeId) throws NoContentException {
		Recipe recipeToDelete = recipeRepository.findById(recipeId).orElseThrow(
				NoContentException::new);
		recipeRepository.delete(recipeToDelete);
	}
}
