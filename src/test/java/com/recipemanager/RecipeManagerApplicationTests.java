package com.recipemanager;

import com.recipemanager.controller.FoodController;
import com.recipemanager.controller.UnitController;
import com.recipemanager.util.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = RecipeManagerApplication.class)
class RecipeManagerApplicationTests {

	@Autowired
	private UnitController unitController;
	@Autowired
	private FoodController foodController;

	@IntegrationTest
	void contextLoads() {
		assertThat(unitController).isNotNull();
		assertThat(foodController).isNotNull();
	}

}
