package de.kochen.food.controller;

import de.kochen.food.util.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FoodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @IntegrationTest
    void getFoodById_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/food/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Kuchen'}"))
                .andExpect(jsonPath("$.unitName").value("Stück"));
    }

    @IntegrationTest
    void getFoodById_ReturnsFailureWhenFoodNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/food/3")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}