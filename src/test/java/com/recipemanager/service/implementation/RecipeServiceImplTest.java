package com.recipemanager.service.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeFoodValidator;
import com.recipemanager.validator.RecipeHeaderValidator;
import com.recipemanager.validator.RecipeValidator;
import com.recipemanager.validator.implementation.RecipeFoodValidatorImpl;
import com.recipemanager.validator.implementation.RecipeHeaderValidatorImpl;
import com.recipemanager.validator.implementation.RecipeValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {
	private final RecipeRepository recipeRepository = mock(RecipeRepository.class);
	private final RecipeHeaderRepository recipeHeaderRepository = mock(RecipeHeaderRepository.class);
	private final RecipeHeaderValidator recipeHeaderValidator = new RecipeHeaderValidatorImpl(recipeHeaderRepository);
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final RecipeFoodValidator foodValidator = new RecipeFoodValidatorImpl(foodRepository);
	private final RecipeValidator recipeValidator = new RecipeValidatorImpl(recipeHeaderValidator, foodValidator);
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

	@Test
	void getRecipeByIdThrowsWhenNothingFound() {
		when(recipeRepository.findByRecipeHeaderId(1L)).thenReturn(List.of());

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeByRecipeHeaderId(999L));
	}

	@Test
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

	@Test
	void postRecipeThrowsWhenUsingId() {

		assertThrows(IdNotAllowedException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void postRecipeThrowsWhenMissingAnIdForRecipeHeader() {
		recipe.setId(null);
		recipe.setRecipeHeader(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));

	}

	@Test
	void postRecipeThrowsWhenMissingAnIdForFood() {
		recipe.setId(null);
		recipe.setFood(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void postRecipeThrowsWhenQuantityIsNull() {
		recipe.setId(null);
		recipe.setQuantity(null);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void postRecipeThrowsWhenRecipeHeaderNotFound() {
		recipe.setId(null);
		when(recipeHeaderRepository.existsById(recipe.getRecipeHeader().getId())).thenReturn(false);
		when(foodRepository.existsById(any())).thenReturn(true);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void postRecipeThrowsWhenFoodNotFound() {
		recipe.setId(null);
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(recipe.getRecipeHeader().getId())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void postRecipeReturnsRecipeWithSavedId() throws NotFoundException, IdNotAllowedException {
		Long newId = 7L;
		recipe.setId(null);
		Recipe savedRecipe = new Recipe(newId, recipe.getRecipeHeader(), recipe.getFood(), recipe.getQuantity());
		when(recipeRepository.save(recipe)).thenReturn(savedRecipe);
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(any())).thenReturn(true);

		Recipe result = recipeService.postRecipe(recipe);

		assertEquals(newId, result.getId());
		assertEquals(recipe.getRecipeHeader().getName(), result.getRecipeHeader().getName());
		assertEquals(recipe.getFood().getName(), result.getFood().getName());
		assertEquals(recipe.getQuantity(), result.getQuantity());
	}

	@Test
	void putRecipeThrowsWhenRecipeIdNotExists() {
		Long wrongId = 999L;
		recipe.setId(wrongId);
		when(recipeRepository.findById(wrongId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipe));
	}

	@Test
	void putRecipeThrowsWhenRecipeHeaderIdNotExists() {
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeHeaderRepository.existsById(any())).thenReturn(false);
		when(foodRepository.existsById(any())).thenReturn(true);

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipe));
	}

	@Test
	void putRecipeThrowsWhenFoodIdNotExists() {
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(any())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipe));
	}

	@Test
	void putRecipeReturnsRecipeWhenSaved() throws NotFoundException {
		Recipe putRecipe = new Recipe(recipe.getId(), recipe.getRecipeHeader(), recipe.getFood(), 1.11);
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(any())).thenReturn(true);
		when(recipeRepository.save(recipe)).thenReturn(putRecipe);

		Recipe savedRecipe = recipeService.putRecipe(putRecipe);

		assertEquals(recipe.getQuantity(), savedRecipe.getQuantity());
		assertEquals(recipe.getRecipeHeader().getName(), savedRecipe.getRecipeHeader().getName());
		assertEquals(recipe.getFood().getName(), savedRecipe.getFood().getName());
		assertEquals(recipe.getFood().getUnit().getName(), savedRecipe.getFood().getUnit().getName());
	}

	@Test
	void putRecipeReturnsOldRecipeWhenNoParametersAreIn() throws NotFoundException {
		Recipe putRecipe = new Recipe(recipe.getId(), null, null, null);
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(any())).thenReturn(true);
		when(recipeRepository.save(recipe)).thenReturn(recipe);

		Recipe savedRecipe = recipeService.putRecipe(putRecipe);

		assertNotNull(savedRecipe.getQuantity());
		assertNotNull(savedRecipe.getRecipeHeader());
		assertNotNull(savedRecipe.getFood());
	}

	@Test
	void deleteThrowsWhenRecipeIsNotInRepository() {
		when(recipeRepository.existsById(any())).thenReturn(false);

		assertThrows(NoContentException.class, () -> recipeService.delete(recipe.getId()));
	}

	@Test
	void deleteDontThrowsWhenRecipeIsDeleted() {
		when(recipeRepository.existsById(any())).thenReturn(true);

		assertDoesNotThrow(() -> recipeService.delete(recipe.getId()));
	}

}