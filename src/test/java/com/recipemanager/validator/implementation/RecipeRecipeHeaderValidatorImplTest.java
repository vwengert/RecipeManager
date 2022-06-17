package com.recipemanager.validator.implementation;

import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeRecipeHeaderValidator;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeRecipeHeaderValidatorImplTest {
	private final RecipeHeaderRepository recipeHeaderRepository = mock(RecipeHeaderRepository.class);
	private final RecipeRecipeHeaderValidator recipeRecipeHeaderValidator = new RecipeRecipeHeaderValidatorImpl(recipeHeaderRepository);

	private RecipeHeader recipeHeader;
	private Recipe originalRecipe;

	@BeforeEach
	void setUp() {
		recipeHeader = new RecipeHeader(1L, "name", "description", 2);
		originalRecipe = new Recipe(1L, recipeHeader, null, 0.0);
	}

	@UnitTest
	void checkNewRecipeHeaderChangesRecipeToOriginalRecipeHeaderWhenNull() throws NotFoundException {
		Recipe recipe = new Recipe(2L, null, null, 0.0);

		recipeRecipeHeaderValidator.checkNewRecipeHeader(recipe, originalRecipe);

		assertEquals(originalRecipe.getRecipeHeader(), recipe.getRecipeHeader());
	}

	@UnitTest
	void checkNewRecipeHeaderThrowWhenRecipeRecipeHeaderIsNotInRepository() {
		Recipe recipe = new Recipe(2L, recipeHeader, null, 0.0);
		when(recipeHeaderRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeRecipeHeaderValidator.checkNewRecipeHeader(recipe, originalRecipe));
	}

	@UnitTest
	void checkNewRecipeHeaderNotThrowingWhenRecipeRecipeHeaderIsInRepositoryAndNotNull() {
		Recipe recipe = new Recipe(2L, recipeHeader, null, 0.0);
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeRecipeHeaderValidator.checkNewRecipeHeader(recipe, this.originalRecipe));
	}

	@UnitTest
	void checkRecipeHeaderThrowsWhenRecipeHeaderIsNotInRepository() {
		when(recipeHeaderRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeRecipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipeHeader));
	}

	@UnitTest
	void checkRecipeHeaderNotThrowsWhenRecipeHeaderIsInRepository() {
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeRecipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipeHeader));
	}

}