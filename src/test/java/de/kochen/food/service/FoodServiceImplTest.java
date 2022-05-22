package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodServiceImplTest {
    FoodRepository foodRepository = mock(FoodRepository.class);
    FoodServiceImpl foodService = new FoodServiceImpl(foodRepository, new ModelMapper());

    @UnitTest
    public void getUnitByIdReturnsFood() throws NotFoundException {
        when(foodRepository.findById(1L)).thenReturn(Optional.of(new Food(1L, "cake", new Unit(1L, "piece"))));

        FoodDto foodDto = foodService.getFoodById(1L);

        assertEquals("cake", foodDto.getName());
        assertEquals("piece", foodDto.getUnitName());
    }

    @UnitTest
    public void getUnitByIdThrowsWhenNotFound() {
        when(foodRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> foodService.getFoodById(3L));
    }

}