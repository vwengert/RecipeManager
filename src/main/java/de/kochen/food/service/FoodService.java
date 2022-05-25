package de.kochen.food.service;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    /**
     * @param foodId Id to search for food
     * @return Food with Baseunit
     * @throws NotFoundException Food not found
     */
    FoodDto getFoodById(UUID foodId) throws NotFoundException;

    /**
     * @return get List of all Food
     */
    List<FoodDto> getFood();

    /**
     * @param name of Food to search
     * @return Food if found
     * @throws NotFoundException Food not found
     */
    FoodDto getFoodByName(String name) throws NotFoundException;

    /**
     * @param foodDto neues anzulegendes Food
     * @return neu angelegtes Food
     */
    FoodDto postFood(FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException;
}
