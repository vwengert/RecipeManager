package com.recipemanager.validator.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeFoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeFoodValidatorImpl implements RecipeFoodValidator {
	private final FoodRepository foodRepository;

	public void checkNewFood(Recipe recipe, Recipe originalRecipe) throws NotFoundException {
		if (recipe.getFood() == null)
			recipe.setFood(originalRecipe.getFood());
		else
			checkFoodExistsOrElseThrowException(recipe.getFood());
	}

	public void checkFoodExistsOrElseThrowException(Food food) throws NotFoundException {
		if (!foodRepository.existsById(food.getId()))
			throw new NotFoundException();
	}

}