package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.Recipe;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.annotations.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {
	private final long recipeNotFound = 9999L;
	private final String noRecipeToFind = "NoRecipeToFind";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	RecipeRepository recipeRepository;
	Recipe recipe, secondRecipe, notSavedRecipe;

	@BeforeEach
	@Transactional
	void setUp() {
		recipe = recipeRepository.save(new Recipe(null, "Suppe", "kochen", 2));
		secondRecipe = recipeRepository.save(new Recipe(null, "Salate", "anmachen", 4));
		notSavedRecipe = new Recipe(null, "Cremesuppe", "cremig kochen", 33);
	}


	@IntegrationTest
	@Transactional
	void getRecipeById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/recipeById/" + recipe.getRecipeId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(recipe.getName()))
				.andExpect(jsonPath("$.description").value(recipe.getDescription()))
				.andExpect(jsonPath("$.portions").value(recipe.getPortions()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeById_ReturnsFailureWhenRecipeNotExists() throws Exception {

		mockMvc.perform(get("/api/v1/recipeById/" + recipeNotFound)
						.contentType("application/json"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getRecipeReturnsArray() throws Exception {

		mockMvc.perform(get("/api/v1/recipe")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(recipe.getName()))
				.andExpect(jsonPath("$[1].name").value(secondRecipe.getName()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeByName_ReturnsRecipe() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByName/" + recipe.getName())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'" + recipe.getName() + "'}"));
	}

	@IntegrationTest
	@Transactional
	void getRecipeByName_ThrowsWhenNoRecipeFound() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByName/" + noRecipeToFind)
						.contentType("application/json"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postRecipeReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/recipe")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + notSavedRecipe.getName() +
								"\",\"description\":\"" + notSavedRecipe.getDescription() +
								"\",\"portions\":\"" + notSavedRecipe.getPortions() + "\"}"))
				.andExpect(status().isCreated());

		assertEquals(notSavedRecipe.getName(), recipeRepository.findByName(notSavedRecipe.getName())
				.orElse(new Recipe(null, "", "", 0))
				.getName());
		assertEquals(notSavedRecipe.getDescription(), recipeRepository.findByName(notSavedRecipe.getName())
				.orElse(new Recipe(null, "", "", 0))
				.getDescription());
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/recipe")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + recipe.getName() +
								"\",\"description\":\"" + recipe.getDescription() +
								"\",\"portions\":\"" + recipe.getPortions() + "\"}"))
				.andExpect(status().isOk());
	}

//	@IntegrationTest
//	@Transactional
//	void putFoodReturns200WhenChangedNameOfFood() throws Exception {
//
//		mockMvc.perform(put("/api/v1/food")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{\"id\":\"" + food.getId() + "\",\"name\":\"Käse\",\"unitName\":\"" + unit.getName() + "\"}"))
//				.andExpect(status().isOk());
//
//		assertEquals("Käse", foodRepository.findById(food.getId()).orElse(new Food(1L, "", new Unit(1L, ""))).getName());
//	}
//
//	@IntegrationTest
//	@Transactional
//	void putFoodReturns200WhenChangedUnitOfFood() throws Exception {
//
//		mockMvc.perform(put("/api/v1/food")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{\"id\":\"" + food.getId() + "\",\"name\":\"" + food.getName() + "\",\"unitName\":\"" + changedUnit.getName() + "\"}"))
//				.andExpect(status().isOk());
//
//		assertEquals(changedUnit.getName(), foodRepository.findById(food.getId()).orElse(new Food(1L, "", new Unit(1L, ""))).getUnit().getName());
//	}
//
//
//	@IntegrationTest
//	@Transactional
//	void deleteReturns200WhenFoodIsDeleted() throws Exception {
//		Unit unit = unitRepository.save(new Unit(null, "unit not deleting!"));
//		Food food = foodRepository.save(new Food(null, "delete me", unit));
//
//		mockMvc.perform(delete("/api/v1/food/" + unit.getId())
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//
//		assertTrue(unitRepository.existsById(unit.getId()));
//		assertFalse(foodRepository.existsById(food.getId()));
//	}
//
//	@IntegrationTest
//	@Transactional
//	void deleteTrowsNotFoundWhenFoodNotThere() throws Exception {
//		Unit unit = new Unit(9999L, "i don't exist");
//		Food food = new Food(9999L, "i don't exist", unit);
//
//		mockMvc.perform(delete("/api/v1/food/" + unit.getId())
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//	}

}