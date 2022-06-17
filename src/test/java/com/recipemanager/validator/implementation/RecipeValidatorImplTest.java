package com.recipemanager.validator.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.FoodValidator;
import com.recipemanager.validator.RecipeRecipeHeaderValidator;
import com.recipemanager.validator.RecipeValidator;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class RecipeValidatorImplTest {
	private final RecipeRecipeHeaderValidator recipeRecipeHeaderValidator = mock(RecipeRecipeHeaderValidator.class);
	private final FoodValidator foodValidator = mock(FoodValidator.class);
	private final RecipeValidator recipeValidator = new RecipeValidatorImpl(recipeRecipeHeaderValidator, foodValidator);

	private Recipe orginialRecipe;
	private Recipe recipe;

	@BeforeEach
	void setUp() {
		orginialRecipe = new Recipe(
				1L,
				new RecipeHeader(1L, "name", "description", 2),
				new Food(1L, "food", new Unit(1L, "unit")),
				2.0);
		recipe = new Recipe(
				1L,
				new RecipeHeader(2L, "newName", "newDescription", 2),
				new Food(1L, "newFood", new Unit(1L, "unit")),
				2.0);
	}

	@UnitTest
	void validateNewRecipeAndFixEmptyFieldsReplacesNullValuesWithOriginalValues() throws NotFoundException {
		recipe.setRecipeHeader(null);
		recipe.setFood(null);
		recipe.setQuantity(null);
		recipeValidator.validateNewRecipeAndFixEmptyFields(recipe, orginialRecipe);

		assertEquals(recipe.getRecipeHeader(), orginialRecipe.getRecipeHeader());
		assertEquals(recipe.getFood(), orginialRecipe.getFood());
		assertEquals(recipe.getQuantity(), orginialRecipe.getQuantity());
	}

	@UnitTest
	void validateNewRecipeAndFixEmptyFieldsThrowsNotFoundWhenRecipeHeaderNotInRepository() throws NotFoundException {
		doThrow(NotFoundException.class)
				.when(recipeRecipeHeaderValidator).checkNewRecipeHeader(any(), any());

		assertThrows(NotFoundException.class, () -> recipeValidator.validateNewRecipeAndFixEmptyFields(recipe, orginialRecipe));
	}

	@UnitTest
	void validateNewRecipeAndFixEmptyFieldsThrowsNotFoundWhenFoodNotInRepository() throws NotFoundException {
		doThrow(NotFoundException.class)
				.when(foodValidator).checkFoodExistsOrElseThrowException(any());

		assertThrows(NotFoundException.class, () -> recipeValidator.validateNewRecipeAndFixEmptyFields(recipe, orginialRecipe));
	}


	@UnitTest
	void checkIfRecipeHeaderAndFoodIdExistsThrowsWhenRecipeHeaderNotExist() throws NotFoundException {
		doThrow(NotFoundException.class)
				.when(recipeRecipeHeaderValidator).checkRecipeHeaderExistsOrElseThrowException(any());

		assertThrows(NotFoundException.class, () -> recipeValidator.checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(recipe));
	}

	@UnitTest
	void checkIfRecipeHeaderAndFoodIdExistsThrowsWhenFoodNotExist() throws NotFoundException {
		doThrow(NotFoundException.class)
				.when(foodValidator).checkFoodExistsOrElseThrowException(any());

		assertThrows(NotFoundException.class, () -> recipeValidator.checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(recipe));
	}


	@UnitTest
	void checkIfRecipeHeaderAndFoodIdExistsNotThrowsWhenRecipeHeaderAndFoodExists() {

		assertDoesNotThrow(() -> recipeValidator.checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(recipe));
	}

}