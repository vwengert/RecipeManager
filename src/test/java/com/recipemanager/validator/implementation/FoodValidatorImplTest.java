package com.recipemanager.validator.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodValidatorImplTest {
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final FoodValidatorImpl recipeFoodValidator = new FoodValidatorImpl(foodRepository);

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
	void checkFoodThrowWhenRecipeFoodIsNotInRepository() {
		when(foodRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeFoodValidator.checkFoodExistsOrElseThrowException(food));

	}

	@UnitTest
	void checkFoodNotThrowingWhenRecipeFoodIsInRepositoryAndNotNull() {
		when(foodRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeFoodValidator.checkFoodExistsOrElseThrowException(food));

	}

}