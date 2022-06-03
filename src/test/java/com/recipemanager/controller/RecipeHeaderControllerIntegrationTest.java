package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
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
class RecipeHeaderControllerIntegrationTest {
	private final long recipeNotFound = 9999L;
	private final String noRecipeToFind = "NoRecipeToFind";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	RecipeHeaderRepository recipeHEaderRepository;
	RecipeHeader recipeHeader, secondRecipeHeader, notSavedRecipeHeader;

	@BeforeEach
	@Transactional
	void setUp() {
		recipeHeader = recipeHEaderRepository.save(new RecipeHeader(null, "soup", "cook long", 2));
		secondRecipeHeader = recipeHEaderRepository.save(new RecipeHeader(null, "salad", "turn on", 4));
		notSavedRecipeHeader = new RecipeHeader(null, "ravioli", "cook", 33);
	}


	@IntegrationTest
	@Transactional
	void getRecipeHeaderById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/recipeHeaderById/" + recipeHeader.getId()).contentType("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value(recipeHeader.getName())).andExpect(jsonPath("$.description").value(recipeHeader.getDescription())).andExpect(jsonPath("$.portions").value(recipeHeader.getPortions()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeHeaderById_ReturnsFailureWhenRecipeNotExists() throws Exception {

		mockMvc.perform(get("/api/v1/recipeHeaderById/" + recipeNotFound).contentType("application/json")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getRecipeHeaderReturnsArray() throws Exception {

		mockMvc.perform(get("/api/v1/recipeHeader").contentType("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value(recipeHeader.getName())).andExpect(jsonPath("$[1].name").value(secondRecipeHeader.getName()));
	}

	@IntegrationTest
	@Transactional
	void getRecipeHeaderByName_ReturnsRecipe() throws Exception {

		mockMvc.perform(get("/api/v1/recipeHeaderByName/" + recipeHeader.getName()).contentType("application/json")).andExpect(status().isOk()).andExpect(content().json("{'name':'" + recipeHeader.getName() + "'}"));
	}

	@IntegrationTest
	@Transactional
	void getRecipeHeaderByName_ThrowsWhenNoRecipeFound() throws Exception {

		mockMvc.perform(get("/api/v1/recipeHeaderByName/" + noRecipeToFind).contentType("application/json")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postRecipeHeaderReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/recipeHeader").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + notSavedRecipeHeader.getName() + "\",\"description\":\"" + notSavedRecipeHeader.getDescription() + "\",\"portions\":\"" + notSavedRecipeHeader.getPortions() + "\"}")).andExpect(status().isCreated());

		assertEquals(notSavedRecipeHeader.getName(), recipeHEaderRepository.findByName(notSavedRecipeHeader.getName()).orElse(new RecipeHeader(null, "", "", 0)).getName());
		assertEquals(notSavedRecipeHeader.getDescription(), recipeHEaderRepository.findByName(notSavedRecipeHeader.getName()).orElse(new RecipeHeader(null, "", "", 0)).getDescription());
	}

	@IntegrationTest
	@Transactional
	void postRecipeHeaderReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/recipeHeader").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"" + recipeHeader.getName() + "\",\"description\":\"" + recipeHeader.getDescription() + "\",\"portions\":\"" + recipeHeader.getPortions() + "\"}")).andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putRecipeHeaderReturns200WhenAnyChangeOfRecipeIsDone() throws Exception {
		recipeHeader.setName("change");
		recipeHeader.setDescription("changed description");
		recipeHeader.setPortions(33);

		mockMvc.perform(put("/api/v1/recipeHeader").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"" + recipeHeader.getId() + "\",\"name\":\"" + recipeHeader.getName() + "\",\"description\":\"" + recipeHeader.getDescription() + "\",\"portions\":\"" + recipeHeader.getPortions() + recipeHeader.getPortions() + "\"}")).andExpect(status().isOk());

		assertEquals(recipeHeader.getName(), recipeHEaderRepository.findById(recipeHeader.getId()).orElse(new RecipeHeader(1L, "", "", 0)).getName());
	}

	@IntegrationTest
	@Transactional
	void putRecipeHeaderThrowsNotFoundWhenIdOfRecipeIsNotThere() throws Exception {
		notSavedRecipeHeader.setId(recipeNotFound);

		mockMvc.perform(put("/api/v1/recipeHeader").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"" + notSavedRecipeHeader.getId() + "\",\"name\":\"" + notSavedRecipeHeader.getName() + "change" + "\",\"description\":\"" + notSavedRecipeHeader.getDescription() + "change" + "\",\"portions\":\"" + notSavedRecipeHeader.getPortions() + "\"}")).andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void deleteReturns200WhenRecipeHeaderIsDeleted() throws Exception {

		mockMvc.perform(delete("/api/v1/recipeHeader/" + recipeHeader.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertFalse(recipeHEaderRepository.existsById(recipeHeader.getId()));
	}

	@IntegrationTest
	@Transactional
	void deleteTrowsNotFoundWhenRecipeHeaderNotThere() throws Exception {
		notSavedRecipeHeader.setId(999L);

		mockMvc.perform(delete("/api/v1/recipeHeader/" + notSavedRecipeHeader.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}