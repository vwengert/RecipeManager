package com.recipemanager.validator.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodValidatorImplTest {
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final RecipeFoodValidatorImpl foodValidator = new RecipeFoodValidatorImpl(foodRepository);

	private Unit unit;
	private Food food;
	private Recipe originalRecipe;

	@BeforeEach
	void setUp() {
		unit = new Unit(1L, "Unit");
		food = new Food(1L, "Testfood", unit);
		originalRecipe = new Recipe(1L, null, food, 0.0);
	}

	@UnitTest
	void checkNewFoodChangeRecipeFoodToOriginalRecipeWhenRecipeFoodIsNull() throws NotFoundException {
		Recipe recipe = new Recipe(2L, null, null, 0.0);

		foodValidator.checkNewFood(recipe, originalRecipe);

		assertEquals(recipe.getFood(), originalRecipe.getFood());
	}

	@UnitTest
	void checkNewFoodThrowWhenRecipeFoodIsNotInRepository() throws NotFoundException {
		Recipe recipe = new Recipe(2L, null, new Food(2L, "NotThere", unit), 0.0);
		when(foodRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> foodValidator.checkNewFood(recipe, this.originalRecipe));

	}

	@UnitTest
	void checkNewFoodNotThrowingWhenRecipeFoodIsInRepositoryAndNotNull() throws NotFoundException {
		Recipe recipe = new Recipe(2L, null, new Food(2L, "NotThere", unit), 0.0);
		when(foodRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> foodValidator.checkNewFood(recipe, this.originalRecipe));

	}

	@UnitTest
	void checkFoodthrowWhenRecipeFoodIsNotInRepository() throws NotFoundException {
		when(foodRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> foodValidator.checkFoodExistsOrElseThrowException(food));

	}

	@UnitTest
	void checkFoodnotThrowingWhenRecipeFoodIsInRepositoryAndNotNull() throws NotFoundException {
		when(foodRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> foodValidator.checkFoodExistsOrElseThrowException(food));

	}

}