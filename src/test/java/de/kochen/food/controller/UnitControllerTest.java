package de.kochen.food.controller;

import de.kochen.food.util.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UnitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @IntegrationTest
    void getUnitById_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/unit/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Stück'}"));

    }

    @IntegrationTest
    void getUnitById_ReturnsFailureWhenFoodNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/unit/3")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}