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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
						.content(createContentStringFrom(recipeSuppe)))
				.andExpect(status().isNotAcceptable());
	}

	@IntegrationTest
	@Transactional
	void postReturnNotFoundWhenRecipeNotExists() throws Exception {
		RecipeHeader recipeHeader = RecipeHeader.builder()
				.id(999L)
				.portions(3)
				.name("no saved Header")
				.description("not saved yet")
				.build();
		Recipe recipe = Recipe.builder()
				.recipeHeader(recipeHeader)
				.food(recipeSuppe.getFood())
				.quantity(recipeSuppe.getQuantity())
				.build();

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postReturnNotFoundWhenFoodNotExists() throws Exception {
		Food food = Food.builder()
				.id(9999L)
				.name("not yet there")
				.unit(stk)
				.build();
		Recipe recipe = Recipe.builder()
				.recipeHeader(recipeSuppe.getRecipeHeader())
				.food(food)
				.quantity(recipeSuppe.getQuantity())
				.build();

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void postReturn201AndNewRecipe() throws Exception {
		Recipe recipe = Recipe.builder()
				.recipeHeader(recipeSuppe.getRecipeHeader())
				.food(recipeSuppe.getFood())
				.quantity(recipeSuppe.getQuantity())
				.build();

		mockMvc.perform(post("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isCreated());
	}

	@IntegrationTest
	@Transactional
	void putReturnsNotFoundWhenIdNotExist() throws Exception {
		Recipe recipe = Recipe.builder()
				.id(9999L)
				.recipeHeader(recipeSuppe.getRecipeHeader())
				.food(recipeSuppe.getFood())
				.quantity(recipeSuppe.getQuantity())
				.build();

		mockMvc.perform(put("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void putReturnsNotFoundWhenRecipeHeaderIdNotExist() throws Exception {
		RecipeHeader header = RecipeHeader.builder()
				.id(9999L)
				.name("not yet there")
				.description("nothing to find")
				.portions(4)
				.build();
		Recipe recipe = Recipe.builder()
				.id(recipeSuppe.getId())
				.quantity(3.0)
				.recipeHeader(header)
				.food(recipeSuppe.getFood())
				.build();

		mockMvc.perform(put("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void putReturnsNotFoundWhenFoodIdNotExist() throws Exception {
		Food food = Food.builder()
				.id(9999L)
				.name("not yet there")
				.unit(stk)
				.build();
		Recipe recipe = Recipe.builder()
				.id(recipeSuppe.getId())
				.quantity(3.0)
				.recipeHeader(recipeSuppe.getRecipeHeader())
				.food(food)
				.build();

		mockMvc.perform(put("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void putReturns200AndChangedToNewRecipeHeader() throws Exception {
		Recipe recipe = recipeSuppe;
		recipe.setRecipeHeader(brot);

		mockMvc.perform(put("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipe)))
				.andExpect(status().isOk());

		recipeRepository.flush();
		Recipe newRecipe = recipeRepository.findById(recipeSuppe.getId()).orElse(new Recipe());
		assertEquals(brot.getName(), newRecipe.getRecipeHeader().getName());
	}

	@IntegrationTest
	@Transactional
	void putReturns200AndChangedToNewFood() throws Exception {
		Recipe recipe = recipeSuppe;
		recipe.setFood(kaese);

		mockMvc.perform(put("/api/v1/recipe/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createContentStringFrom(recipeSuppe)))
				.andExpect(status().isOk());

		recipeRepository.flush();
		Recipe newRecipe = recipeRepository.findById(recipeSuppe.getId()).orElse(new Recipe());
		assertEquals(kaese.getName(), newRecipe.getFood().getName());
	}

	String createContentStringFrom(Recipe recipe) {
		return "{\"id\":" + recipe.getId() + "," +
				"\"recipeHeaderId\":" + recipe.getRecipeHeader().getId() + "," +
				"\"recipeHeaderName\":\"" + recipe.getRecipeHeader().getName() + "\"," +
				"\"recipeHeaderDescription\":\"" + recipe.getRecipeHeader().getDescription() + "\"," +
				"\"recipeHeaderPortions\":" + recipe.getRecipeHeader().getPortions() + "," +
				"\"foodId\":" + recipe.getFood().getId() + "," +
				"\"foodName\":\"" + recipe.getFood().getName() + "\"," +
				"\"foodUnitId\":" + recipe.getFood().getUnit().getId() + "," +
				"\"foodUnitName\":\"" + recipe.getFood().getUnit().getName() + "\"," +
				"\"quantity\":" + recipe.getQuantity() + "}";
	}

	@IntegrationTest
	@Transactional
	void deleteReturns200WhenRecipeIsDeleted() throws Exception {
		Unit unit = unitRepository.save(new Unit(null, "unit not deleting!"));
		Food food = foodRepository.save(new Food(null, "delete me", unit));
		RecipeHeader recipeHeader = recipeHeaderRepository.save(new RecipeHeader(null, "don't delete Recipe", "new Recipe not to delete", 3));
		Recipe recipe = recipeRepository.save(new Recipe(null, recipeHeader, food, 3.0));

		mockMvc.perform(delete("/api/v1/recipe/" + recipe.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertTrue(unitRepository.existsById(unit.getId()));
		assertTrue(foodRepository.existsById(food.getId()));
		assertTrue(recipeHeaderRepository.existsById(recipeHeader.getId()));
		assertFalse(recipeRepository.existsById(recipe.getId()));
	}

	@IntegrationTest
	@Transactional
	void deleteTrowsNotFoundWhenRecipeIsNotThere() throws Exception {
		Unit unit = new Unit(9999L, "i don't exist");
		Food food = new Food(9999L, "i don't exist", unit);
		RecipeHeader recipeHeader = new RecipeHeader(9999L, "i don't exist", "i really don't exist", 3);
		Recipe recipe = new Recipe(9999L, recipeHeader, food, 3.0);

		mockMvc.perform(delete("/api/v1/recipe/" + recipe.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}