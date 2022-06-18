package com.recipemanager.service.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeValidator;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
	private final RecipeRepository recipeRepository = mock(RecipeRepository.class);
	private final RecipeValidator recipeValidator = mock(RecipeValidator.class);
	private final RecipeService recipeService = new RecipeServiceImpl(recipeRepository, recipeValidator);

	private Recipe recipe;

	@BeforeEach
	void setUp() {
		recipe = new Recipe(
				1L,
				new RecipeHeader(1L, "Suppe", "kochen", 4),
				new Food(1L, "Kartoffel", new Unit(1L, "Stück")),
				0.4);
	}

	@UnitTest
	void getRecipeByIdThrowsWhenNothingFound() {
		when(recipeRepository.findByRecipeHeaderId(1L)).thenReturn(List.of());

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeByRecipeHeaderId(999L));
	}

	@UnitTest
	void getRecipeByIdReturnsAList() throws NotFoundException {
		when(recipeRepository.findByRecipeHeaderId(1L)).thenReturn(List.of(recipe));

		List<Recipe> recipeList = recipeService.getRecipeByRecipeHeaderId(1L);

		assertNotNull(recipeList);
		Recipe first = recipeList.get(0);
		assertEquals("Suppe", first.getRecipeHeader().getName());
		assertEquals("Kartoffel", first.getFood().getName());
		assertEquals("Stück", first.getFood().getUnit().getName());
		assertEquals(0.4, first.getQuantity());

	}

	@UnitTest
	void postRecipeThrowsWhenUsingId() {

		assertThrows(IdNotAllowedException.class, () -> recipeService.postRecipe(recipe));
	}

	@UnitTest
	void postRecipeThrowsWhenMissingAnIdForRecipeHeader() {
		recipe.setId(null);
		recipe.setRecipeHeader(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));

	}

	@UnitTest
	void postRecipeThrowsWhenMissingAnIdForFood() {
		recipe.setId(null);
		recipe.setFood(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@UnitTest
	void postRecipeThrowsWhenQuantityIsNull() {
		recipe.setId(null);
		recipe.setQuantity(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@UnitTest
	void postRecipeThrowsWhenRecipeHeaderOrFoodNotFound() throws NotFoundException {
		recipe.setId(null);
		doThrow(NotFoundException.class)
				.when(recipeValidator).checkIfRecipeHeaderAndFoodIdExistsOrElseThrowException(any());

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@UnitTest
	void postRecipeReturnsRecipeWithSavedId() throws NotFoundException, IdNotAllowedException {
		Long newId = 7L;
		recipe.setId(null);
		Recipe savedRecipe = new Recipe(newId, recipe.getRecipeHeader(), recipe.getFood(), recipe.getQuantity());
		when(recipeRepository.save(recipe)).thenReturn(savedRecipe);

		Recipe result = recipeService.postRecipe(recipe);

		assertEquals(newId, result.getId());
		assertEquals(recipe.getRecipeHeader().getName(), result.getRecipeHeader().getName());
		assertEquals(recipe.getFood().getName(), result.getFood().getName());
		assertEquals(recipe.getQuantity(), result.getQuantity());
	}

	@UnitTest
	void putRecipeThrowsWhenRecipeIdNotExists() {
		Long wrongId = 999L;
		recipe.setId(wrongId);
		when(recipeRepository.findById(wrongId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipe));
	}

	@UnitTest
	void putRecipeThrowsWhenRecipeHeaderOrFoodIdNotExists() throws NotFoundException {
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		doThrow(NotFoundException.class)
				.when(recipeValidator).validateNewRecipeAndFixEmptyFields(any(), any());

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipe));
	}

	@UnitTest
	void putRecipeReturnsRecipeWhenSaved() throws NotFoundException {
		Recipe putRecipe = new Recipe(recipe.getId(), recipe.getRecipeHeader(), recipe.getFood(), 1.11);
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeRepository.save(any())).thenReturn(putRecipe);

		Recipe savedRecipe = recipeService.putRecipe(putRecipe);

		assertEquals(1.11, savedRecipe.getQuantity());
		assertEquals(recipe.getRecipeHeader().getName(), savedRecipe.getRecipeHeader().getName());
		assertEquals(recipe.getFood().getName(), savedRecipe.getFood().getName());
		assertEquals(recipe.getFood().getUnit().getName(), savedRecipe.getFood().getUnit().getName());
	}

	@UnitTest
	void putRecipeReturnsOldRecipeWhenNoParametersAreIn() throws NotFoundException {
		Recipe putRecipe = new Recipe(recipe.getId(), null, null, null);
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeRepository.save(any())).thenReturn(recipe);

		Recipe savedRecipe = recipeService.putRecipe(putRecipe);

		assertNotNull(savedRecipe.getQuantity());
		assertNotNull(savedRecipe.getRecipeHeader());
		assertNotNull(savedRecipe.getFood());
	}

	@UnitTest
	void deleteThrowsWhenRecipeIsNotInRepository() {
		when(recipeRepository.existsById(any())).thenReturn(false);

		assertThrows(NoContentException.class, () -> recipeService.delete(recipe.getId()));
	}

	@UnitTest
	void deleteDontThrowsWhenRecipeIsDeleted() {
		when(recipeRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeService.delete(recipe.getId()));
	}

}