package com.recipemanager.service;

import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {
	private final long recipeId = 1L;
	private final int portions = 2;
	private final long recipeNotFoundId = 999L;
	RecipeRepository recipeRepository = mock(RecipeRepository.class);
	RecipeService recipeService = new RecipeServiceImpl(recipeRepository);
	private final Recipe recipeSuppe = new Recipe(recipeId, "Suppe", "kochen", portions);
	private final Recipe secondRecipe = new Recipe(recipeId, "Salat", "anmachen", portions + portions);
	private final List<Recipe> recipes = List.of(recipeSuppe, secondRecipe);

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
		assertEquals(secondRecipe.getRecipeId(), recipeList.get(1).getRecipeId());
		assertEquals(secondRecipe.getPortions(), recipeList.get(1).getPortions());
	}

//	@UnitTest
//	public void getFoodByNameReturnsFood() throws NotFoundException {
//		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));
//
//		Food food = foodService.getFoodByName("cake");
//
//		assertNotNull(food);
//		assertEquals("cake", food.getName());
//		assertEquals("piece", food.getUnit().getName());
//	}
//
//	@UnitTest
//	public void getFoodByNameThrowsWhenNameNotFound() {
//
//		assertThrows(NotFoundException.class, () -> foodService.getFoodByName("apple"));
//	}
//
//	@UnitTest
//	public void postFoodThrowsIdNotAllowedWhenRequestSendsIds() {
//		foodDtoList.get(0).setId(1L);
//		foodDtoList.get(1).setUnitId(1L);
//		foodDtoList.get(2).setId(1L);
//		foodDtoList.get(2).setUnitId(1L);
//
//		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
//		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(1).getFood()));
//		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(2).getFood()));
//	}
//
//	@UnitTest
//	public void postFoodThrowsNotFoundExceptionWhenUnitNameNotExists() {
//		when(unitRepository.findByName("piece")).thenReturn(Optional.empty());
//
//		assertThrows(NotFoundException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
//	}
//
//	@UnitTest
//	public void postFoodThrowsReturns200WhenFoodNameIsUsed() {
//		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));
//
//		assertThrows(FoundException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
//	}
//
//	@UnitTest
//	public void postFoodSavesAndReturnsFoodWhenNameIsNewAndUnitExists() throws FoundException, NotFoundException, IdNotAllowedException {
//		when(unitRepository.findByName("piece")).thenReturn(Optional.of(unitList.get(0)));
//		when(foodRepository.findByName("apple")).thenReturn(Optional.empty());
//		when(foodRepository.save(any())).thenReturn(new Food(3L, "apple", new Unit(1L, "piece")));
//
//		FoodDto foodDto = FoodDto.getFoodDto(foodService.postFood(foodDtoList.get(2).getFood()));
//		assertEquals("apple", foodDto.getName());
//		assertEquals("piece", foodDto.getUnitName());
//	}
//
//	@UnitTest
//	public void putFoodSavesChangedFoodWhenIdExists() throws NotFoundException {
//		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
//		when(unitRepository.findByName(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
//		FoodDto foodDtoToChange = foodDtoList.get(0);
//		foodDtoToChange.setName("changed");
//		when(foodRepository.save(any()))
//				.thenReturn(new Food(foodDtoToChange.getId(), foodDtoToChange.getName(),
//						new Unit(foodDtoToChange.getUnitId(), foodDtoToChange.getUnitName())));
//
//		FoodDto foodDto = FoodDto.getFoodDto(foodService.putFood(foodDtoToChange.getFood()));
//		assertEquals(foodDtoList.get(0).getName(), foodDto.getName());
//	}
//
//	@UnitTest
//	public void putFoodSavesChangedUnitWhenIdExists() throws NotFoundException {
//		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
//		when(unitRepository.findByName(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
//		FoodDto foodDtoToChange = foodDtoList.get(0);
//		foodDtoToChange.setUnitName("changed");
//		when(foodRepository.save(any()))
//				.thenReturn(new Food(foodDtoToChange.getId(), foodDtoToChange.getName(),
//						new Unit(foodDtoToChange.getUnitId(), foodDtoToChange.getUnitName())));
//
//		FoodDto foodDto = FoodDto.getFoodDto(foodService.putFood(foodDtoToChange.getFood()));
//
//		assertEquals(foodDtoList.get(0).getUnitName(), foodDto.getUnitName());
//	}
//
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