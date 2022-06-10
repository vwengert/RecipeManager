package com.recipemanager.service;

import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {
	RecipeRepository recipeRepository = mock(RecipeRepository.class);
	RecipeService recipeService = new RecipeServiceImpl(recipeRepository);

	@BeforeEach
	void setUp() {

	}

	@Test
	void getRecipeByIdThrowsWhenNothingFound() {

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeByRecipeHeaderId(999L));
	}

	@Test
	void getRecipeByIdReturnsAListOfFoods() throws NotFoundException {
		when(recipeRepository.findByRecipeHeaderId(1L)).thenReturn(
				List.of(new Recipe(
						1L,
						new RecipeHeader(1L, "Suppe", "kochen", 4),
						new Food(1L, "Kartoffel", new Unit(1L, "Stück")),
						0.4)));

		List<Recipe> recipeList = recipeService.getRecipeByRecipeHeaderId(1L);

		assertNotNull(recipeList);
		Recipe first = recipeList.get(0);
		assertEquals("Suppe", first.getRecipeHeader().getName());
		assertEquals("Kartoffel", first.getFood().getName());
		assertEquals("Stück", first.getFood().getUnit().getName());
		assertEquals(0.4, first.getQuantity());

	}
}