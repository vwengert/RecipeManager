package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {
	private final long recipeId = 1L;
	private final int portions = 2;
	private final long recipeNotFoundId = 999L;
	private final Recipe recipeSuppe = new Recipe(recipeId, "Suppe", "kochen", portions);
	private final Recipe secondRecipe = new Recipe(recipeId, "Salat", "anmachen", portions + portions);
	private final List<Recipe> recipes = List.of(recipeSuppe, secondRecipe);
	private final Recipe notSavedRecipe = new Recipe(null, "Cremesuppe", "cremig kochen", portions + portions + portions);
	RecipeRepository recipeRepository = mock(RecipeRepository.class);
	RecipeService recipeService = new RecipeServiceImpl(recipeRepository);

	@BeforeEach
	public void setUp() {
	}

	@UnitTest
	public void getRecipeByIdReturnsFood() throws NotFoundException {
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeSuppe));

		Recipe recipe = recipeService.getRecipeById(recipeId);

		assertNotNull(recipe);
		assertEquals(recipeSuppe.getName(), recipe.getName());
		assertEquals(recipeSuppe.getDescription(), recipe.getDescription());
		assertEquals(recipeSuppe.getPortions(), recipe.getPortions());
	}

	@UnitTest
	public void getRecipeByIdThrowsWhenNotFound() {
		when(recipeRepository.findById(recipeNotFoundId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(recipeNotFoundId));
	}

	@UnitTest
	public void getRecipeList() {
		when(recipeRepository.findAll()).thenReturn(recipes);

		List<Recipe> recipeList = recipeService.getRecipe();

		assertNotNull(recipeList);
		assertEquals(recipeSuppe.getName(), recipeList.get(0).getName());
		assertEquals(recipeSuppe.getPortions(), recipeList.get(0).getPortions());
		assertEquals(secondRecipe.getId(), recipeList.get(1).getId());
		assertEquals(secondRecipe.getPortions(), recipeList.get(1).getPortions());
	}

	@UnitTest
	public void getRecipeByNameReturnsRecipe() throws NotFoundException {
		when(recipeRepository.findByName(recipeSuppe.getName())).thenReturn(Optional.of(recipeSuppe));

		Recipe result = recipeService.getRecipeByName(recipeSuppe.getName());

		assertNotNull(result);
		assertEquals(recipeSuppe.getName(), result.getName());
		assertEquals(recipeSuppe.getDescription(), result.getDescription());
		assertEquals(recipeSuppe.getPortions(), result.getPortions());
	}

	@UnitTest
	public void getRecipeByNameThrowsWhenNameNotFound() {

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeByName(notSavedRecipe.getName()));
	}

	@UnitTest
	public void postRecipeThrowsIdNotAllowedWhenRequestSendsIds() {
		notSavedRecipe.setId(1L);

		assertThrows(IdNotAllowedException.class, () -> recipeService.postRecipe(notSavedRecipe));
	}

	@UnitTest
	public void postRecipeThrowsReturns200WhenRecipeNameIsUsed() {
		when(recipeRepository.existsByName(recipeSuppe.getName())).thenReturn(true);
		recipeSuppe.setId(null);

		assertThrows(FoundException.class, () -> recipeService.postRecipe(recipeSuppe));
	}

	@UnitTest
	public void postRecipeSavesAndReturnsRecipeWhenNameIsNew() throws FoundException, IdNotAllowedException {
		when(recipeRepository.existsByName(any())).thenReturn(false);
		when(recipeRepository.findByName(any())).thenReturn(Optional.empty());
		when(recipeRepository.save(any())).thenReturn(secondRecipe);

		secondRecipe.setId(null);
		Recipe recipe = recipeService.postRecipe(secondRecipe);
		assertEquals(secondRecipe.getName(), recipe.getName());
		assertEquals(secondRecipe.getDescription(), recipe.getDescription());
		assertEquals(secondRecipe.getPortions(), recipe.getPortions());
	}

	@UnitTest
	public void putRecipeSavesChangedRecipeWhenIdExists() throws NotFoundException {
		Recipe recipeChanged = new Recipe(recipeSuppe.getId(), "changed", recipeSuppe.getDescription(), recipeSuppe.getPortions());
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipeSuppe));
		when(recipeRepository.save(any())).thenReturn(recipeChanged);

		Recipe recipe = recipeService.putRecipe(recipeSuppe);
		assertEquals(recipeSuppe.getId(), recipeChanged.getId());
		assertEquals(recipeChanged.getName(), recipe.getName());
		assertEquals(recipeChanged.getId(), recipe.getId());
	}

	@UnitTest
	public void putRecipeThrowsNotFoundWhenRecipeNotExists() throws NotFoundException {
		Recipe recipeChanged = new Recipe(recipeNotFoundId, "changed", recipeSuppe.getDescription(), recipeSuppe.getPortions());
		when(recipeRepository.findById(recipeChanged.getId())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeService.putRecipe(recipeSuppe));
	}

//	@UnitTest
//	public void deleteThrowsNotFoundWhenFoodNotExists() {
//		when(foodRepository.findById(any())).thenReturn(Optional.empty());
//
//		assertThrows(NoContentException.class, () -> foodService.deleteFood(8888L));
//	}
//
//	@UnitTest
//	public void deleteOnlyDeletesFoodButNotUnit() {
//		Unit unit = new Unit(13L, "don't delete me");
//		Food food = new Food(13L, "delete me", unit);
//		when(foodRepository.findById(any())).thenReturn(Optional.of(food));
//
//		assertDoesNotThrow(() -> foodService.deleteFood(food.getId()));
//	}

}