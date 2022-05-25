package de.kochen.food.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        unitStueck = unitRepository.saveAndFlush(unitStueck);
        unitKg = new Unit(null, "kg");
        unitKg = unitRepository.saveAndFlush(unitKg);
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

        assertEquals("Meter", unitRepository.findByName("Meter").get().getName());
        assertNotNull(unitRepository.findByName("Meter").get().getId());
    }

    @IntegrationTest
    @Transactional
    void postUnitReturns200IfFoodAlreadyExists() throws Exception {

        mockMvc.perform(post("/api/v1/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Stück\"}"))
                .andExpect(status().isOk());
    }

}