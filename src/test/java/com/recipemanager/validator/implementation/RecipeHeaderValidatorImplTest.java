package com.recipemanager.validator.implementation;

import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeHeaderValidator;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeHeaderValidatorImplTest {
	private final RecipeHeaderRepository recipeHeaderRepository = mock(RecipeHeaderRepository.class);
	private final RecipeHeaderValidator recipeHeaderValidator = new RecipeHeaderValidatorImpl(recipeHeaderRepository);

	private RecipeHeader recipeHeader;
	private Recipe originalRecipe;

	@BeforeEach
	void setUp() {
		recipeHeader = new RecipeHeader(1L, "name", "description", 2);
		originalRecipe = new Recipe(1L, recipeHeader, null, 0.0);
	}

	@UnitTest
	void checkRecipeHeaderThrowsWhenRecipeHeaderIsNotInRepository() {
		when(recipeHeaderRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipeHeader));
	}

	@UnitTest
	void checkRecipeHeaderNotThrowsWhenRecipeHeaderIsInRepository() {
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeHeaderValidator.checkRecipeHeaderExistsOrElseThrowException(recipeHeader));
	}

}