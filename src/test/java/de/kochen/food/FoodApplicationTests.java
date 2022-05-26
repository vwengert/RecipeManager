package de.kochen.food;

import de.kochen.food.controller.FoodController;
import de.kochen.food.controller.UnitController;
import de.kochen.food.util.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = FoodApplication.class)
class FoodApplicationTests {

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
