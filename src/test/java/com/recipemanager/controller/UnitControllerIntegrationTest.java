package com.recipemanager.controller;

import com.recipemanager.RecipeManagerApplication;
import com.recipemanager.model.Unit;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.annotations.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
class UnitControllerIntegrationTest {
	private final String cheese = "Käse";
	private final String piece = "piece";
	private final String kg = "kg";
	private final String meter = "meter";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	UnitRepository unitRepository;
	private Unit unitPiece;
	private Unit unitKg;

	@BeforeEach
	@Transactional
	void setUp() {
		unitPiece = new Unit(null, piece);
		unitPiece = unitRepository.save(unitPiece);
		unitKg = new Unit(null, kg);
		unitKg = unitRepository.save(unitKg);
	}

	@IntegrationTest
	@Transactional
	void getUnitById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/unitById/" + unitPiece.getId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'" + piece + "'}"));

	}

	@IntegrationTest
	@Transactional
	void getUnitById_ReturnsFailureWhenFoodNotExists() throws Exception {
		Long id = unitKg.getId();
		unitRepository.delete(unitKg);

		mockMvc.perform(get("/api/v1/unitById/" + id)
						.contentType("application/json"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getUnitByName_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/unitByName/" + unitPiece.getName())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'" + piece + "'}"));

	}

	@IntegrationTest
	@Transactional
	void getUnitByName_ReturnsFailureWhenFoodNotExists() throws Exception {
		String name = unitKg.getName();
		unitRepository.delete(unitKg);

		mockMvc.perform(get("/api/v1/unitByName/" + name)
						.contentType("application/json"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void getUnitReturnsArray() throws Exception {

		mockMvc.perform(get("/api/v1/unit")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(piece))
				.andExpect(jsonPath("$[1].name").value(kg));
	}

	@IntegrationTest
	@Transactional
	void postUnitReturns201() throws Exception {
		mockMvc.perform(post("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + meter + "\"}"))
				.andExpect(status().isCreated());

		Optional<Unit> unitOptional = unitRepository.findByName(meter);
		assertEquals(meter, unitOptional.orElse(new Unit(1L, "")).getName());
		assertNotNull(unitOptional.orElse(new Unit(null, "")).getId());
	}

	@IntegrationTest
	@Transactional
	void postUnitReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"" + piece + "\"}"))
				.andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putUnitReturnsWith200WhenExistingResourceIsModified() throws Exception {

		mockMvc.perform(put("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"" + unitPiece.getId() + "\",\"name\":\"" + cheese + "\"}"))
				.andExpect(status().isOk());

		Unit unit = unitRepository.findById(unitPiece.getId()).orElse(null);

		assertNotNull(unit);
		assertEquals(cheese, unit.getName());
	}

	@IntegrationTest
	@Transactional
	void putUnitReturnsNotFoundWhenIdNotExists() throws Exception {

		mockMvc.perform(put("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"7777\",\"name\":\"" + cheese + "\"}"))
				.andExpect(status().isNotFound());
	}

	@IntegrationTest
	@Transactional
	void deleteReturns200WhenUnitIsDeleted() throws Exception {
		Unit unit = unitRepository.save(new Unit(null, "delete me"));

		mockMvc.perform(delete("/api/v1/unit/" + unit.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertFalse(unitRepository.existsById(unit.getId()));
	}

	@IntegrationTest
	@Transactional
	void deleteTrowsNotFoundWhenUnitNotThere() throws Exception {
		Unit unit = new Unit(9999L, "i don't exist");

		mockMvc.perform(delete("/api/v1/unit/" + unit.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}