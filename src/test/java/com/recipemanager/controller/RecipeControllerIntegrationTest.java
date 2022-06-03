package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.annotations.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {
	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	@Transactional
	void setUp() {
	}


	@IntegrationTest
	@Transactional
	void getRecipeById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByRecipeHeaderId/1").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].recipeHeaderName").value("Suppe"))
				.andExpect(jsonPath("$[0].foodName").value("Kartoffel"))
				.andExpect(jsonPath("$[0].Quantity").value(3));

//		mockMvc.perform(get("/api/v1/recipeById/2").contentType("application/json"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].recipeHeaderName").value("Brot"))
//				.andExpect(jsonPath("$[0].foodName").value("Käse"))
//				.andExpect(jsonPath("$[0].Quantity").value(1));
	}



}