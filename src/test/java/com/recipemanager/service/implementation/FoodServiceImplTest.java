package com.recipemanager.service.implementation;

import com.recipemanager.model.Food;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.service.FoodService;
import com.recipemanager.util.annotations.UnitTest;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodServiceImplTest {
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final UnitRepository unitRepository = mock(UnitRepository.class);
	private final FoodService foodService = new FoodServiceImpl(foodRepository, unitRepository);

	private final String cake = "cake";
	private final String piece = "piece";
	private final String kg = "kg";
	private final String potato = "potato";
	private final String apple = "apple";
	private List<Unit> unitList;
	private List<Food> foodList;

	@BeforeEach
	public void setUp() {
		unitList = List.of(
				Unit.builder().id(1L).name(piece).build(),
				Unit.builder().id(2L).name(kg).build()
		);
		foodList = List.of(
				Food.builder().id(1L).name(cake).unit(unitList.get(0)).build(),
				Food.builder().id(2L).name(potato).unit(unitList.get(1)).build()
		);
	}

	@UnitTest
	public void getUnitByIdReturnsFood() throws NotFoundException {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(foodList.get(0)));

		Food food = foodService.getFoodById(1L);

		assertNotNull(food);
		assertEquals(cake, food.getName());
		assertEquals(piece, food.getUnit().getName());
	}

	@UnitTest
	public void getUnitByIdThrowsWhenNotFound() {
		when(foodRepository.findById(3L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> foodService.getFoodById(3L));
	}

	@UnitTest
	public void getFoodList() {
		when(foodRepository.findAll()).thenReturn(foodList);

		List<Food> foodList = foodService.getFood();

		assertNotNull(foodList);
		assertEquals(cake, foodList.get(0).getName());
		assertEquals(piece, foodList.get(0).getUnit().getName());
		assertEquals(potato, foodList.get(1).getName());
		assertEquals(kg, foodList.get(1).getUnit().getName());
	}

	@UnitTest
	public void getFoodByNameReturnsFood() throws NotFoundException {
		when(foodRepository.findByName(cake)).thenReturn(Optional.of(foodList.get(0)));

		Food food = foodService.getFoodByName(cake);

		assertNotNull(food);
		assertEquals(cake, food.getName());
		assertEquals(piece, food.getUnit().getName());
	}

	@UnitTest
	public void getFoodByNameThrowsWhenNameNotFound() {

		assertThrows(NotFoundException.class, () -> foodService.getFoodByName(apple));
	}

	@UnitTest
	public void postFoodThrowsIdNotAllowedWhenRequestSendsIds() {

		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodList.get(0)));
		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodList.get(1)));
	}

	@UnitTest
	public void postFoodThrowsNotFoundExceptionWhenUnitNameNotExists() {
		when(unitRepository.findByName(piece)).thenReturn(Optional.empty());
		Food saveNew = Food.builder().name(cake).unit(new Unit(null, kg + "notExists")).build();

		assertThrows(NotFoundException.class, () -> foodService.postFood(saveNew));
	}

	@UnitTest
	public void postFoodThrowsReturns200WhenFoodNameIsUsed() {
		when(foodRepository.existsByName(cake)).thenReturn(true);

		Food saveNew = Food.builder().name(cake).unit(unitList.get(0)).build();

		assertThrows(FoundException.class, () -> foodService.postFood(saveNew));
	}

	@UnitTest
	public void postFoodSavesAndReturnsFoodWhenNameIsNewAndUnitExists() throws FoundException, NotFoundException, IdNotAllowedException {
		when(unitRepository.findById(any())).thenReturn(Optional.of(unitList.get(0)));
		when(foodRepository.findByName(apple)).thenReturn(Optional.empty());
		when(foodRepository.save(any())).thenReturn(new Food(3L, apple, new Unit(1L, piece)));

		Food food = foodService.postFood(new Food(null, apple, new Unit(1L, piece)));
		assertEquals(apple, food.getName());
		assertEquals(piece, food.getUnit().getName());
	}

	@UnitTest
	public void putFoodSavesChangedFoodWhenIdExists() throws NotFoundException {
		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
		when(unitRepository.findById(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
		Food foodToChange = foodList.get(0);
		foodToChange.setName("changed");
		when(foodRepository.save(any()))
				.thenReturn(new Food(foodToChange.getId(), foodToChange.getName(),
						new Unit(foodToChange.getUnit().getId(), foodToChange.getUnit().getName())));

		Food food = foodService.putFood(foodToChange);
		assertEquals(foodList.get(0).getName(), food.getName());
	}

	@UnitTest
	public void putFoodSavesChangedUnitWhenIdExists() throws NotFoundException {
		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
		when(unitRepository.findById(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
		Food FoodToChange = foodList.get(0);
		FoodToChange.getUnit().setName("changed");
		when(foodRepository.save(any()))
				.thenReturn(new Food(FoodToChange.getId(), FoodToChange.getName(),
						new Unit(FoodToChange.getUnit().getId(), FoodToChange.getUnit().getName())));

		Food food = foodService.putFood(FoodToChange);

		assertEquals(foodList.get(0).getUnit().getName(), food.getUnit().getName());
	}

	@UnitTest
	public void deleteThrowsNotFoundWhenFoodNotExists() {
		when(foodRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NoContentException.class, () -> foodService.deleteFood(8888L));
	}

	@UnitTest
	public void deleteOnlyDeletesFoodButNotUnit() {
		Unit unit = new Unit(13L, "don't delete me");
		Food food = new Food(13L, "delete me", unit);
		when(foodRepository.findById(any())).thenReturn(Optional.of(food));

		assertDoesNotThrow(() -> foodService.deleteFood(food.getId()));
	}

}