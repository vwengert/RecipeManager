package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {
	private final RecipeRepository recipeRepository = mock(RecipeRepository.class);
	private final RecipeHeaderRepository recipeHeaderRepository = mock(RecipeHeaderRepository.class);
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final RecipeService recipeService = new RecipeServiceImpl(recipeRepository, recipeHeaderRepository, foodRepository);

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
	void putRecipeThrowsWhenUsingId() {

		assertThrows(IdNotAllowedException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void putRecipeThrowsWhenRecipeHeaderNotFound() {
		recipe.setId(null);
		when(recipeHeaderRepository.existsById(recipe.getRecipeHeader().getId())).thenReturn(false);
		when(foodRepository.existsById(any())).thenReturn(true);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void putRecipeThrowsWhenFoodNotFound() {
		recipe.setId(null);
		when(recipeHeaderRepository.existsById(any())).thenReturn(true);
		when(foodRepository.existsById(recipe.getRecipeHeader().getId())).thenReturn(false);

		assertThrows(NotFoundException.class, () -> recipeService.postRecipe(recipe));
	}

	@Test
	void putRecipeReturnsRecipeWithSavedId() throws NotFoundException, IdNotAllowedException {
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

}