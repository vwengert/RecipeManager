package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodServiceImplTest {
	private final FoodRepository foodRepository = mock(FoodRepository.class);
	private final UnitRepository unitRepository = mock(UnitRepository.class);
	private final FoodServiceImpl foodService = new FoodServiceImpl(foodRepository, unitRepository, new ModelMapper());

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

		FoodDto foodDto = foodService.getFoodById(1L);

		assertNotNull(foodDto);
		assertEquals("cake", foodDto.getName());
		assertEquals("piece", foodDto.getUnitName());
	}

	@UnitTest
	public void getUnitByIdThrowsWhenNotFound() {
		when(foodRepository.findById(3L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> foodService.getFoodById(3L));
	}

	@UnitTest
	public void getFoodList() {
		when(foodRepository.findAll()).thenReturn(foodList);

		List<FoodDto> foodDtoList = foodService.getFood();

		assertNotNull(foodDtoList);
		assertEquals("cake", foodDtoList.get(0).getName());
		assertEquals("piece", foodDtoList.get(0).getUnitName());
		assertEquals("potato", foodDtoList.get(1).getName());
		assertEquals("kg", foodDtoList.get(1).getUnitName());
	}

	@UnitTest
	public void getFoodByNameReturnsFood() throws NotFoundException {
		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));

		FoodDto foodDto = foodService.getFoodByName("cake");

		assertNotNull(foodDto);
		assertEquals("cake", foodDto.getName());
		assertEquals("piece", foodDto.getUnitName());
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

		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(0)));
		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(1)));
		assertThrows(IdNotAllowedException.class, () -> foodService.postFood(foodDtoList.get(2)));
	}

	@UnitTest
	public void postFoodThrowsNotFoundExceptionWhenUnitNameNotExists() {
		when(unitRepository.findByName("piece")).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> foodService.postFood(foodDtoList.get(0)));
	}

	@UnitTest
	public void postFoodThrowsReturns200WhenFoodNameIsUsed() {
		when(foodRepository.findByName("cake")).thenReturn(Optional.of(foodList.get(0)));

		assertThrows(FoundException.class, () -> foodService.postFood(foodDtoList.get(0)));
	}

	@UnitTest
	public void postFoodSavesAndReturnsFoodWhenNameIsNewAndUnitExists() throws FoundException, NotFoundException, IdNotAllowedException {
		when(unitRepository.findByName("piece")).thenReturn(Optional.of(unitList.get(0)));
		when(foodRepository.findByName("apple")).thenReturn(Optional.empty());
		when(foodRepository.save(any())).thenReturn(new Food(3L, "apple", new Unit(1L, "piece")));

		FoodDto foodDto = foodService.postFood(foodDtoList.get(2));
		assertEquals("apple", foodDto.getName());
		assertEquals("piece", foodDto.getUnitName());
	}

}