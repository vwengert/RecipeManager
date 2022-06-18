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
	public Recipe validateNewRecipeAndFixEmptyFields(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		return validateNewRecipe(recipe, originalRecipe);
	}

	@Override
	public void checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(Recipe recipe) throws NotFoundException {
		recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
		foodValidator.checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	private Recipe validateNewRecipe(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		recipe = validateAndChangeQuantity(recipe, originalRecipe);
		recipe = validateAndChangeRecipeHeader(recipe, originalRecipe);
		return validateAndChangeFood(recipe, originalRecipe);
	}

	private Recipe validateAndChangeFood(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getFood() == null)
			recipe.setFood(originalRecipe.getFood());
		else
			foodValidator.checkFoodExistsOrElseThrowException(recipe.getFood());
		return recipe;
	}

	private Recipe validateAndChangeRecipeHeader(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getRecipeHeader() == null)
			recipe.setRecipeHeader(originalRecipe.getRecipeHeader());
		else
			recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
		return recipe;
	}

	private Recipe validateAndChangeQuantity(Recipe recipe, Recipe originalRecipe) {
		if (recipe.getQuantity() == null)
			recipe.setQuantity(originalRecipe.getQuantity());
		return recipe;
	}

}
