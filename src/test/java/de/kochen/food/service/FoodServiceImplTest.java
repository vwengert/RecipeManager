package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodServiceImplTest {
    private final FoodRepository foodRepository = mock(FoodRepository.class);
    private final FoodService foodService = new FoodServiceImpl(foodRepository, new ModelMapper());

    private List<Food> foodList;
    @BeforeEach
    public void setUp() {
        List<Unit> unitList = List.of(
                Unit.builder().guid(1L).name("piece").build(),
                Unit.builder().guid(2L).name("kg").build()
        );
        foodList =  List.of(
                Food.builder().guid(1L).name("cake").unit(unitList.get(0)).build(),
                Food.builder().guid(2L).name("potato").unit(unitList.get(1)).build()
                );
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

}