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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodServiceImplTest {
    private final FoodRepository foodRepository = mock(FoodRepository.class);
    private final UnitRepository unitRepository = mock(UnitRepository.class);
    private final FoodService foodService = new FoodServiceImpl(foodRepository, unitRepository, new ModelMapper());

    private List<Unit> unitList;
    private List<Food> foodList;
    private List<FoodDto> foodDtoList;

    @BeforeEach
    public void setUp() {
        unitList = List.of(
                Unit.builder().id(new UUID(1, 1)).name("piece").build(),
                Unit.builder().id(new UUID(2, 2)).name("kg").build()
        );
        foodList = List.of(
                Food.builder().id(new UUID(1, 1)).name("cake").unit(unitList.get(0)).build(),
                Food.builder().id(new UUID(2, 2)).name("potato").unit(unitList.get(1)).build()
        );
        foodDtoList = List.of(
                FoodDto.builder().name("cake").unitName("piece").build(),
                FoodDto.builder().name("potato").unitName("kd").build(),
                FoodDto.builder().name("apple").unitName("piece").build());
    }

    @UnitTest
    public void getUnitByIdReturnsFood() throws NotFoundException {
        when(foodRepository.findById(new UUID(1, 1))).thenReturn(Optional.of(foodList.get(0)));

        FoodDto foodDto = foodService.getFoodById(new UUID(1, 1));

        assertNotNull(foodDto);
        assertEquals("cake", foodDto.getName());
        assertEquals("piece", foodDto.getUnitName());
    }

    @UnitTest
    public void getUnitByIdThrowsWhenNotFound() {
        when(foodRepository.findById(new UUID(3, 3))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> foodService.getFoodById(new UUID(3, 3)));
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
        foodDtoList.get(0).setGuid(new UUID(1, 1));
        foodDtoList.get(1).setUnitGuid(new UUID(1, 1));
        foodDtoList.get(2).setGuid(new UUID(1, 1));
        foodDtoList.get(2).setUnitGuid(new UUID(1, 1));

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
        when(foodRepository.save(any())).thenReturn(new Food(new UUID(3, 3), "apple", new Unit(new UUID(1, 1), "piece")));

        FoodDto foodDto = foodService.postFood(foodDtoList.get(2));
        assertEquals("apple", foodDto.getName());
        assertEquals("piece", foodDto.getUnitName());
    }

}