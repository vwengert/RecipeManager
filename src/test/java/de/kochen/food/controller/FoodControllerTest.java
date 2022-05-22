package de.kochen.food.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FoodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFoodById_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/food/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Kuchen'}"));
    }

    @Test
    void getFoodById_ReturnsFailureWhenFoodNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/food/3")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}