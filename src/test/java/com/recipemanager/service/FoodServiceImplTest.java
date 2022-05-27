package com.recipemanager.service;

import com.recipemanager.dto.FoodDto;
import com.recipemanager.model.Food;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.*;
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
	private final FoodServiceImpl foodService = new FoodServiceImpl(foodRepository, unitRepository);

	private List<Unit> unitList;
	private List<Food> foodList;
	private List<FoodDto> foodDtoList;

	@BeforeEach
	public void setUp() {
		unitList = List.of(
				Unit.builder().id(1L).name("piece").build(),
				Unit.builder().id(2L).name("kg").build()
		);
		foodList = List.of(
				Food.builder().id(1L).name("cake").unit(unitList.get(0)).build(),
				Food.builder().id(2L).name("potato").unit(unitList.get(1)).build()
		);
		foodDtoList = List.of(
				FoodDto.builder().name("cake").unitName("piece").build(),
				FoodDto.builder().name("potato").unitName("kd").build(),
				FoodDto.builder().name("apple").unitName("piece").build());
	}

	@UnitTest
	public void getUnitByIdReturnsFood() throws NotFoundException {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(foodList.get(0)));

		Food food = foodService.getFoodById(1L);

		assertNotNull(food);
		assertEquals("cake", food.getName());
		assertEquals("piece", food.getUnit().getName());
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
		assertEquals("cake", foodList.get(0).getName());
		assertEquals("piece", foodList.get(0).getUnit().getName());
		assertEquals("potato", foodList.get(1).getName());
		assertEquals("kg", foodList.get(1).getUnit().getName());
	}

	@UnitTest
	public void getFoodByNameReturnsFood() throws NotFoundException {
		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));

		Food food = foodService.getFoodByName("cake");

		assertNotNull(food);
		assertEquals("cake", food.getName());
		assertEquals("piece", food.getUnit().getName());
	}

	@UnitTest
	public void getFoodByNameThrowsWhenNameNotFound() {

		assertThrows(NotFoundException.class, () -> foodService.getFoodByName("apple"));
	}

	@UnitTest
	public void postFoodThrowsIdNotAllowedWhenRequestSendsIds() {
		foodDtoList.get(0).setId(1L);
		foodDtoList.get(1).setUnitId(1L);
		foodDtoList.get(2).setId(1L);
		foodDtoList.get(2).setUnitId(1L);

		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(1).getFood()));
		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(2).getFood()));
	}

	@UnitTest
	public void postFoodThrowsNotFoundExceptionWhenUnitNameNotExists() {
		when(unitRepository.findByName("piece")).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
	}

	@UnitTest
	public void postFoodThrowsReturns200WhenFoodNameIsUsed() {
		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));

		assertThrows(FoundException.class, () -> foodService.postFood(foodDtoList.get(0).getFood()));
	}

	@UnitTest
	public void postFoodSavesAndReturnsFoodWhenNameIsNewAndUnitExists() throws FoundException, NotFoundException, IdNotAllowedException {
		when(unitRepository.findByName("piece")).thenReturn(Optional.of(unitList.get(0)));
		when(foodRepository.findByName("apple")).thenReturn(Optional.empty());
		when(foodRepository.save(any())).thenReturn(new Food(3L, "apple", new Unit(1L, "piece")));

		FoodDto foodDto = FoodDto.getFoodDto(foodService.postFood(foodDtoList.get(2).getFood()));
		assertEquals("apple", foodDto.getName());
		assertEquals("piece", foodDto.getUnitName());
	}

	@UnitTest
	public void putFoodSavesChangedFoodWhenIdExists() throws NotFoundException {
		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
		when(unitRepository.findByName(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
		FoodDto foodDtoToChange = foodDtoList.get(0);
		foodDtoToChange.setName("changed");
		when(foodRepository.save(any()))
				.thenReturn(new Food(foodDtoToChange.getId(), foodDtoToChange.getName(),
						new Unit(foodDtoToChange.getUnitId(), foodDtoToChange.getUnitName())));

		FoodDto foodDto = FoodDto.getFoodDto(foodService.putFood(foodDtoToChange.getFood()));
		assertEquals(foodDtoList.get(0).getName(), foodDto.getName());
	}

	@UnitTest
	public void putFoodSavesChangedUnitWhenIdExists() throws NotFoundException {
		when(foodRepository.findById(any())).thenReturn(Optional.of(foodList.get(0)));
		when(unitRepository.findByName(any())).thenReturn(Optional.of(foodList.get(0).getUnit()));
		FoodDto foodDtoToChange = foodDtoList.get(0);
		foodDtoToChange.setUnitName("changed");
		when(foodRepository.save(any()))
				.thenReturn(new Food(foodDtoToChange.getId(), foodDtoToChange.getName(),
						new Unit(foodDtoToChange.getUnitId(), foodDtoToChange.getUnitName())));

		FoodDto foodDto = FoodDto.getFoodDto(foodService.putFood(foodDtoToChange.getFood()));

		assertEquals(foodDtoList.get(0).getUnitName(), foodDto.getUnitName());
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