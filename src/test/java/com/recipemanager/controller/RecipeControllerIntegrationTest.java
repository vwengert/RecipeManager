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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
		recipe = recipeRepository.save(new Recipe(null, "soup", "cook long", 2));
		secondRecipe = recipeRepository.save(new Recipe(null, "salad", "turn on", 4));
		notSavedRecipe = new Recipe(null, "ravioli", "cook", 33);
	}


	@IntegrationTest
	@Transactional
	void getRecipeById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/recipeById/" + recipe.getId()).contentType("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value(recipe.getName())).andExpect(jsonPath("$.description").value(recipe.getDescription())).andExpect(jsonPath("$.portions").value(recipe.getPortions()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeById_ReturnsFailureWhenRecipeNotExists() throws Exception {

		mockMvc.perform(get("/api/v1/recipeById/" + recipeNotFound).contentType("application/json")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getRecipeReturnsArray() throws Exception {

		mockMvc.perform(get("/api/v1/recipe").contentType("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value(recipe.getName())).andExpect(jsonPath("$[1].name").value(secondRecipe.getName()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeByName_ReturnsRecipe() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByName/" + recipe.getName()).contentType("application/json")).andExpect(status().isOk()).andExpect(content().json("{'name':'" + recipe.getName() + "'}"));
	}

	@IntegrationTest
	@Transactional
	void getRecipeByName_ThrowsWhenNoRecipeFound() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByName/" + noRecipeToFind).contentType("application/json")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postRecipeReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/recipe").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + notSavedRecipe.getName() + "\",\"description\":\"" + notSavedRecipe.getDescription() + "\",\"portions\":\"" + notSavedRecipe.getPortions() + "\"}")).andExpect(status().isCreated());

		assertEquals(notSavedRecipe.getName(), recipeRepository.findByName(notSavedRecipe.getName()).orElse(new Recipe(null, "", "", 0)).getName());
		assertEquals(notSavedRecipe.getDescription(), recipeRepository.findByName(notSavedRecipe.getName()).orElse(new Recipe(null, "", "", 0)).getDescription());
	}

	@IntegrationTest
	@Transactional
	void postFoodReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/recipe").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + recipe.getName() + "\",\"description\":\"" + recipe.getDescription() + "\",\"portions\":\"" + recipe.getPortions() + "\"}")).andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putRecipeReturns200WhenAnyChangeOfRecipeIsDone() throws Exception {
		recipe.setName("change");
		recipe.setDescription("changed description");
		recipe.setPortions(33);

		mockMvc.perform(put("/api/v1/recipe").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"" + recipe.getId() + "\",\"name\":\"" + recipe.getName() + "\",\"description\":\"" + recipe.getDescription() + "\",\"portions\":\"" + recipe.getPortions() + recipe.getPortions() + "\"}")).andExpect(status().isOk());

		assertEquals(recipe.getName(), recipeRepository.findById(recipe.getId()).orElse(new Recipe(1L, "", "", 0)).getName());
	}

	@IntegrationTest
	@Transactional
	void putRecipeThrowsNotFoundWhenIdOfRecipeIsNotThere() throws Exception {
		notSavedRecipe.setId(recipeNotFound);

		mockMvc.perform(put("/api/v1/recipe").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"" + notSavedRecipe.getId() + "\",\"name\":\"" + notSavedRecipe.getName() + "change" + "\",\"description\":\"" + notSavedRecipe.getDescription() + "change" + "\",\"portions\":\"" + notSavedRecipe.getPortions() + "\"}")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void deleteReturns200WhenRecipeIsDeleted() throws Exception {

		mockMvc.perform(delete("/api/v1/recipe/" + recipe.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertFalse(recipeRepository.existsById(recipe.getId()));
	}

	@IntegrationTest
	@Transactional
	void deleteTrowsNotFoundWhenRecipeNotThere() throws Exception {
		notSavedRecipe.setId(999L);

		mockMvc.perform(delete("/api/v1/recipe/" + notSavedRecipe.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}