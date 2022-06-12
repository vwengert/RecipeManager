package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.Food;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.annotations.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
class FoodControllerIntegrationTest {
	private final String piece = "piece";
	private final String cake = "cake";
	private final String potato = "potato";
	private final String kg = "kg";
	private final String apple = "apple";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	FoodRepository foodRepository;
	@Autowired
	UnitRepository unitRepository;
	private Food foodKuchen;
	private Food foodKartoffeln;
	private Unit unitPiece;
	private Unit unitKg;
	private Unit changedUnit;
	private Unit unit;
	private Food food;

	@BeforeEach
	@Transactional
	void setUp() {
		unitPiece = new Unit(null, piece);
		unitPiece = unitRepository.save(unitPiece);
		unitKg = new Unit(null, kg);
		unitKg = unitRepository.save(unitPiece);

		foodKuchen = new Food(null, cake, unitPiece);
		foodKuchen = foodRepository.save(foodKuchen);
		foodKartoffeln = new Food(null, potato, unitKg);
		foodKartoffeln = foodRepository.save(foodKartoffeln);

		unit = unitRepository.save(new Unit(null, "AnyUnit"));
		food = foodRepository.save(new Food(null, "change me", unit));
		changedUnit = unitRepository.save(new Unit(null, "ChangedUnit"));
	}


	@IntegrationTest
	@Transactional
	void getFoodById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/foodById/" + foodKuchen.getId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'" + cake + "'}"))
				.andExpect(jsonPath("$.unitName").value(piece));
	}

	@IntegrationTest
	@Transactional
	void getFoodById_ReturnsFailureWhenFoodNotExists() throws Exception {
		Long id = foodKartoffeln.getId();
		foodRepository.delete(foodKartoffeln);

		mockMvc.perform(get("/api/v1/foodById/" + id)
						.contentType("application/json"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getFoodReturnsArray() throws Exception {

		mockMvc.perform(get("/api/v1/food")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(cake))
				.andExpect(jsonPath("$[1].name").value(potato));
	}

	@IntegrationTest
	@Transactional
	void getFoodByName_ReturnsFood() throws Exception {

		mockMvc.perform(get("/api/v1/foodByName/" + cake)
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'" + cake + "'}"));
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + apple + "\",\"unitName\":\"" + piece + "\"}"))
				.andExpect(status().isCreated());

		assertEquals(apple, foodRepository.findByName(apple).orElse(new Food(null, "", new Unit(null, ""))).getName());
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + cake + "\",\"unitName\":\"" + piece + "\"}"))
				.andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putFoodReturns200WhenChangedNameOfFood() throws Exception {

		mockMvc.perform(put("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"" + food.getId() + "\",\"name\":\"" + apple + "\",\"unitName\":\"" + unit.getName() + "\"}"))
				.andExpect(status().isOk());

		assertEquals(apple, foodRepository.findById(food.getId()).orElse(new Food(1L, "", new Unit(1L, ""))).getName());
	}

	@IntegrationTest
	@Transactional
	void putFoodReturns200WhenChangedUnitOfFood() throws Exception {

		mockMvc.perform(put("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"" + food.getId() + "\",\"name\":\"" + food.getName() + "\",\"unitName\":\"" + changedUnit.getName() + "\"}"))
				.andExpect(status().isOk());

		assertEquals(changedUnit.getName(), foodRepository.findById(food.getId()).orElse(new Food(1L, "", new Unit(1L, ""))).getUnit().getName());
	}

	@IntegrationTest
	@Transactional
	void deleteReturns200WhenFoodIsDeleted() throws Exception {
		Unit unit = unitRepository.save(new Unit(null, "unit not deleting!"));
		Food food = foodRepository.save(new Food(null, "delete me", unit));

		mockMvc.perform(delete("/api/v1/food/" + unit.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertTrue(unitRepository.existsById(unit.getId()));
		assertFalse(foodRepository.existsById(food.getId()));
	}

	@IntegrationTest
	@Transactional
	void deleteTrowsNotFoundWhenFoodNotThere() throws Exception {
		Unit unit = new Unit(9999L, "i don't exist");
		Food food = new Food(9999L, "i don't exist", unit);

		mockMvc.perform(delete("/api/v1/food/" + unit.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}