package de.kochen.food.controller;

import de.kochen.food.FoodApplication;
import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FoodApplication.class)
@AutoConfigureMockMvc
class FoodControllerIntegrationTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	FoodRepository foodRepository;
	@Autowired
	UnitRepository unitRepository;

	Food foodKuchen;
	Food foodKartoffeln;
	Unit unitStueck;
	Unit unitKg;
	Unit changedUnit;
	Unit unit;
	Food food;

	@BeforeEach
	@Transactional
	void setUp() {
		unitStueck = new Unit(null, "Stück");
		unitStueck = unitRepository.save(unitStueck);
		unitKg = new Unit(null, "kg");
		unitKg = unitRepository.save(unitStueck);

		foodKuchen = new Food(null, "Kuchen", unitStueck);
		foodKuchen = foodRepository.save(foodKuchen);
		foodKartoffeln = new Food(null, "Kartoffeln", unitKg);
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
				.andExpect(content().json("{'name':'Kuchen'}"))
				.andExpect(jsonPath("$.unitName").value("Stück"));
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
				.andExpect(jsonPath("$[0].name").value("Kuchen"))
				.andExpect(jsonPath("$[1].name").value("Kartoffeln"));
	}

	@IntegrationTest
	@Transactional
	void getFoodByName_ReturnsFood() throws Exception {

		mockMvc.perform(get("/api/v1/foodByName/Kuchen")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'Kuchen'}"));
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Apfel\",\"unitName\":\"Stück\"}"))
				.andExpect(status().isCreated());

		assertEquals("Apfel", foodRepository.findByName("Apfel").orElse(new Food(null, "", new Unit(null, ""))).getName());
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Kuchen\",\"unitName\":\"Stück\"}"))
				.andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putFoodReturns200WhenChangedNameOfFood() throws Exception {

		mockMvc.perform(put("/api/v1/food")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"" + food.getId() + "\",\"name\":\"Käse\",\"unitName\":\"" + unit.getName() + "\"}"))
				.andExpect(status().isOk());

		assertEquals("Käse", foodRepository.findById(food.getId()).orElse(new Food(1L, "", new Unit(1L, ""))).getName());
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

}