package com.recipemanager.validator.implementation;

import com.recipemanager.model.Recipe;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeFoodValidator;
import com.recipemanager.validator.RecipeRecipeHeaderValidator;
import com.recipemanager.validator.RecipeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeValidatorImpl implements RecipeValidator {
	private final RecipeRecipeHeaderValidator recipeHeaderValidator;
	private final RecipeFoodValidator foodValidator;

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
		checkNewQuantity(recipe, originalRecipe);
		recipeHeaderValidator.checkNewRecipeHeader(recipe, originalRecipe);
		foodValidator.checkNewFood(recipe, originalRecipe);
	}

	private void changeOriginalRecipe(Recipe recipe, Recipe originalRecipe) {
		originalRecipe.setRecipeHeader(recipe.getRecipeHeader());
		originalRecipe.setFood(recipe.getFood());
		originalRecipe.setQuantity(recipe.getQuantity());
	}


	private void checkNewQuantity(Recipe recipe, Recipe originalRecipe) {
		if (recipe.getQuantity() == null)
			recipe.setQuantity(originalRecipe.getQuantity());
	}

}
