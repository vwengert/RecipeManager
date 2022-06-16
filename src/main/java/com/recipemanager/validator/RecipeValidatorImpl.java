package com.recipemanager.validator;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeValidatorImpl implements RecipeValidator {
	private final RecipeHeaderRepository recipeHeaderRepository;
	private final FoodRepository foodRepository;

	@Override
	public void validateNewRecipeAndFixEmptyFields(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		validateNewRecipe(recipe, originalRecipe);
		changeOriginalRecipe(recipe, originalRecipe);
	}

	@Override
	public void checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(Recipe recipe) throws NotFoundException {
		checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
		checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	private void validateNewRecipe(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		checkNewQuantity(recipe, originalRecipe);
		checkNewRecipeHeader(recipe, originalRecipe);
		checkNewFood(recipe, originalRecipe);
	}

	private void changeOriginalRecipe(Recipe recipe, Recipe originalRecipe) {
		originalRecipe.setRecipeHeader(recipe.getRecipeHeader());
		originalRecipe.setFood(recipe.getFood());
		originalRecipe.setQuantity(recipe.getQuantity());
	}

	private void checkNewFood(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getFood() == null)
			recipe.setFood(originalRecipe.getFood());
		else
			checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	private void checkNewRecipeHeader(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getRecipeHeader() == null)
			recipe.setRecipeHeader(originalRecipe.getRecipeHeader());
		else
			checkRecipeHeaderExistsOrElseThrowException(recipe.getRecipeHeader());
	}

	private void checkNewQuantity(Recipe recipe, Recipe originalRecipe) {
		if (recipe.getQuantity() == null)
			recipe.setQuantity(originalRecipe.getQuantity());
	}

	private void checkRecipeHeaderExistsOrElseThrowException(RecipeHeader recipeHeader) throws NotFoundException {
		if (!recipeHeaderRepository.existsById(recipeHeader.getId()))
			throw new NotFoundException();
	}

	private void checkFoodExistsOrElseThrowException(Food food) throws NotFoundException {
		if (!foodRepository.existsById(food.getId()))
			throw new NotFoundException();
	}

}
