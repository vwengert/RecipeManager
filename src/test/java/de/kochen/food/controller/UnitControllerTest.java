package de.kochen.food.controller;

import de.kochen.food.FoodApplication;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FoodApplication.class)
@AutoConfigureMockMvc
class UnitControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	UnitRepository unitRepository;


	Unit unitStueck;
	Unit unitKg;

	@BeforeEach
	@Transactional
	void setUp() {
		unitStueck = new Unit(null, "Stück");
		unitStueck = unitRepository.save(unitStueck);
		unitKg = new Unit(null, "kg");
		unitKg = unitRepository.save(unitKg);
	}

	@IntegrationTest
	@Transactional
	void getUnitById_Returns200() throws Exception {

		mockMvc.perform(get("/api/v1/unitById/" + unitStueck.getId())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'Stück'}"));

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

		mockMvc.perform(get("/api/v1/unitByName/" + unitStueck.getName())
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'name':'Stück'}"));

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
				.andExpect(jsonPath("$[0].name").value("Stück"))
				.andExpect(jsonPath("$[1].name").value("kg"));
	}

	@IntegrationTest
	@Transactional
	void postUnitReturns201() throws Exception {

		mockMvc.perform(post("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Meter\"}"))
				.andExpect(status().isCreated());

		Optional<Unit> unitOptional = unitRepository.findByName("Meter");
		assertEquals("Meter", unitOptional.get().getName());
		assertNotNull(unitOptional.get().getId());
	}

	@IntegrationTest
	@Transactional
	void postUnitReturns200IfFoodAlreadyExists() throws Exception {

		mockMvc.perform(post("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Stück\"}"))
				.andExpect(status().isOk());
	}

	@IntegrationTest
	@Transactional
	void putUnitReturnsWith200WhenExistingResourceIsModified() throws Exception {

		mockMvc.perform(put("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"" + unitStueck.getId() + "\",\"name\":\"Käse\"}"))
				.andExpect(status().isOk());

		Unit unit = unitRepository.findById(unitStueck.getId()).orElse(null);
		assertNotNull(unit);
		assertEquals("Käse", unit.getName());
	}

	@IntegrationTest
	@Transactional
	void putUnitReturnsNotFoundWhenIdNotExists() throws Exception {

		mockMvc.perform(put("/api/v1/unit")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"77\",\"name\":\"Käse\"}"))
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