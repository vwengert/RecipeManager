package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
	private final RecipeRepository recipeRepository;
	private final RecipeValidator recipeValidator;


	@Override
	public List<Recipe> getRecipeByRecipeHeaderId(Long recipeHeaderId) throws NotFoundException {
		List<Recipe> recipeList = recipeRepository.findByRecipeHeaderId(recipeHeaderId);
		if (recipeList.isEmpty()) {
			throw new NotFoundException();
		}
		return recipeList;
	}

	@Override
	public Recipe putRecipe(Recipe recipe) throws NotFoundException {
		Recipe originalRecipe = recipeRepository.findById(recipe.getId()).orElseThrow(NotFoundException::new);

		recipeValidator.validateNewRecipeAndFixEmptyFields(recipe, originalRecipe);
		return recipeRepository.save(originalRecipe);
	}

	@Override
	public Recipe postRecipe(Recipe recipe) throws IdNotAllowedException, NotFoundException {
		if (recipe.getId() != null)
			throw new IdNotAllowedException();
		if (recipe.getRecipeHeader() == null || recipe.getFood() == null || recipe.getQuantity() == null)
			throw new NotFoundException();
		recipeValidator.checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(recipe);

		return recipeRepository.save(recipe);
	}

	@Override
	public void delete(Long id) throws NoContentException {
		if (!recipeRepository.existsById(id))
			throw new NoContentException();

		recipeRepository.deleteById(id);
	}

}
