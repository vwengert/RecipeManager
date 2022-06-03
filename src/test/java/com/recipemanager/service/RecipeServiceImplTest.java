package com.recipemanager.service;

import com.recipemanager.util.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceImplTest {
	RecipeService recipeService = new RecipeServiceImpl();


	@Test
	void getRecipeByIdThrowsWhenNothingFound() {

		assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(999L));
	}
}