package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.FoodRepository;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.annotations.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	RecipeHeaderRepository recipeHeaderRepository;
	@Autowired
	FoodRepository foodRepository;
	@Autowired
	UnitRepository unitRepository;

	Unit stk = new Unit(null, "Stück");
	Food kartoffel = new Food(null, "Kartoffel", stk);
	Food kaese = new Food(null, "Käse", stk);
	RecipeHeader suppe = new RecipeHeader(null, "Suppe", "kochen", 4);
	RecipeHeader brot = new RecipeHeader(null, "Brot", "belegen", 2);
	Recipe recipeSuppe = new Recipe(null, suppe, kartoffel, 3.0);
	Recipe recipeBrot = new Recipe(null, brot, kaese, 1.0);

	@BeforeEach
	@Transactional
	void setUp() {
		stk = unitRepository.save(stk);

		kartoffel = foodRepository.save(kartoffel);
		kaese = foodRepository.save(kaese);

		suppe = recipeHeaderRepository.save(suppe);
		brot = recipeHeaderRepository.save(brot);

		recipeSuppe = recipeRepository.save(recipeSuppe);
		recipeBrot = recipeRepository.save(recipeBrot);
	}

	@AfterEach
	@Transactional
	void tearDown() {
		unitRepository.deleteAll();
		foodRepository.deleteAll();
		recipeHeaderRepository.deleteAll();
		recipeRepository.deleteAll();
	}

	@IntegrationTest
	@Transactional
	void getRecipeByRecipeHeaderId_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByRecipeHeaderId/" + recipeSuppe.getRecipeHeader().getId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpectAll(
						jsonPath("$[0].recipeHeaderName").value("Suppe"),
						jsonPath("$[0].foodUnitName").value("Stück"))
				.andDo(MockMvcResultHandlers.print());

	}

	@IntegrationTest
	@Transactional
	void returnNotFoundWhenIdOfRecipeHeaderIsWrong() throws Exception {

		mockMvc.perform(get("/api/v1/recipeByRecipeHeaderId/" + 333L)
						.contentType("application/json"))
				.andExpect(status().isNotFound());

	}

	@IntegrationTest
	@Transactional
	void postReturnIdNotAllowedWhenIdIsUsed() throws Exception {

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":" + recipeSuppe.getId() + "," +
								"\"recipeHeaderId\":" + recipeSuppe.getRecipeHeader().getId() + "," +
								"\"recipeHeaderName\":\"" + recipeSuppe.getRecipeHeader().getName() + "\"," +
								"\"recipeHeaderDescription\":\"" + recipeSuppe.getRecipeHeader().getDescription() + "\"," +
								"\"recipeHeaderPortions\":" + recipeSuppe.getRecipeHeader().getPortions() + "," +
								"\"foodId\":" + recipeSuppe.getFood().getId() + "," +
								"\"foodName\":\"" + recipeSuppe.getFood().getName() + "\"," +
								"\"foodUnitId\":" + recipeSuppe.getFood().getUnit().getId() + "," +
								"\"foodUnitName\":\"" + recipeSuppe.getFood().getUnit().getName() + "\"," +
								"\"quantity\":" + recipeSuppe.getQuantity() + "}"))
				.andExpect(status().isNotAcceptable());
	}

	@IntegrationTest
	@Transactional
	void postReturnNotFoundWhenRecipeNotExists() throws Exception {

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"recipeHeaderId\":" + recipeSuppe.getRecipeHeader().getId() + 333 + "," +
								"\"recipeHeaderName\":\"" + recipeSuppe.getRecipeHeader().getName() + "\"," +
								"\"recipeHeaderDescription\":\"" + recipeSuppe.getRecipeHeader().getDescription() + "\"," +
								"\"recipeHeaderPortions\":" + recipeSuppe.getRecipeHeader().getPortions() + "," +
								"\"foodId\":" + recipeSuppe.getFood().getId() + "," +
								"\"foodName\":\"" + recipeSuppe.getFood().getName() + "\"," +
								"\"foodUnitId\":" + recipeSuppe.getFood().getUnit().getId() + "," +
								"\"foodUnitName\":\"" + recipeSuppe.getFood().getUnit().getName() + "\"," +
								"\"quantity\":" + recipeSuppe.getQuantity() + "}"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postReturnNotFoundWhenFoodNotExists() throws Exception {

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"recipeHeaderId\":" + recipeSuppe.getRecipeHeader().getId() + "," +
								"\"recipeHeaderName\":\"" + recipeSuppe.getRecipeHeader().getName() + "\"," +
								"\"recipeHeaderDescription\":\"" + recipeSuppe.getRecipeHeader().getDescription() + "\"," +
								"\"recipeHeaderPortions\":" + recipeSuppe.getRecipeHeader().getPortions() + "," +
								"\"foodId\":" + recipeSuppe.getFood().getId() + 333 + "," +
								"\"foodName\":\"" + recipeSuppe.getFood().getName() + "\"," +
								"\"foodUnitId\":" + recipeSuppe.getFood().getUnit().getId() + "," +
								"\"foodUnitName\":\"" + recipeSuppe.getFood().getUnit().getName() + "\"," +
								"\"quantity\":" + recipeSuppe.getQuantity() + "}"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postReturn201AndNewRecipe() throws Exception {

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"recipeHeaderId\":" + recipeSuppe.getRecipeHeader().getId() + "," +
								"\"recipeHeaderName\":\"" + recipeSuppe.getRecipeHeader().getName() + "\"," +
								"\"recipeHeaderDescription\":\"" + recipeSuppe.getRecipeHeader().getDescription() + "\"," +
								"\"recipeHeaderPortions\":" + recipeSuppe.getRecipeHeader().getPortions() + "," +
								"\"foodId\":" + recipeSuppe.getFood().getId() + "," +
								"\"foodName\":\"" + recipeSuppe.getFood().getName() + "\"," +
								"\"foodUnitId\":" + recipeSuppe.getFood().getUnit().getId() + "," +
								"\"foodUnitName\":\"" + recipeSuppe.getFood().getUnit().getName() + "\"," +
								"\"quantity\":" + recipeSuppe.getQuantity() + "}"))
				.andExpect(status().isCreated());
	}

}