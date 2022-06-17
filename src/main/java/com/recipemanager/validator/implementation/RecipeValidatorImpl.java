package com.recipemanager.validator.implementation;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.FoodValidator;
import com.recipemanager.validator.RecipeHeaderValidator;
import com.recipemanager.validator.RecipeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeValidatorImpl implements RecipeValidator {
	private final RecipeHeaderValidator recipeHeaderValidator;
	private final FoodValidator foodValidator;

	@Override
	public void validateNewRecipeAndFixEmptyFields(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		validateNewRecipe(recipe, originalRecipe);
		changeOriginalRecipe(recipe, originalRecipe);
	}

	@Override
	public void checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(Recipe recipe) throws NotFoundException {
		recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
		foodValidator.checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	private void validateNewRecipe(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		validateAndChangeQuantity(recipe, originalRecipe);
		validateAndChangeRecipeHeader(recipe, originalRecipe);
		validateAndChangeFood(recipe, originalRecipe);
	}

	private void validateAndChangeFood(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getFood() == null)
			recipe.setFood(originalRecipe.getFood());
		else
			foodValidator.checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	private void validateAndChangeRecipeHeader(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getRecipeHeader() == null)
			recipe.setRecipeHeader(originalRecipe.getRecipeHeader());
		else
			recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
	}

	private void changeOriginalRecipe(Recipe recipe, Recipe originalRecipe) {
		originalRecipe.setRecipeHeader(recipe.getRecipeHeader());
		originalRecipe.setFood(recipe.getFood());
		originalRecipe.setQuantity(recipe.getQuantity());
	}

	private void validateAndChangeQuantity(Recipe recipe, Recipe originalRecipe) {
		if (recipe.getQuantity() == null)
			recipe.setQuantity(originalRecipe.getQuantity());
	}

}
