package com.recipemanager;

import com.recipemanager.controller.FoodController;
import com.recipemanager.controller.RecipeController;
import com.recipemanager.controller.RecipeHeaderController;
import com.recipemanager.controller.UnitController;
import com.recipemanager.util.annotations.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = RecipeManagerApplication.class)
class RecipeManagerApplicationTests {

	@Autowired
	private UnitController unitController;
	@Autowired
	private FoodController foodController;
	@Autowired
	private RecipeHeaderController recipeHeaderController;
	@Autowired
	private RecipeController recipeController;

	@IntegrationTest
	void contextLoads() {
		assertThat(unitController).isNotNull();
		assertThat(foodController).isNotNull();
		assertThat(recipeHeaderController).isNotNull();
		assertThat(recipeController).isNotNull();
	}

}
