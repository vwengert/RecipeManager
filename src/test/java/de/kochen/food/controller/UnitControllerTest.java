package de.kochen.food.controller;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        UUID id = unitKg.getId();
        unitRepository.delete(unitKg);

        mockMvc.perform(get("/api/v1/unitById/" + id)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

}