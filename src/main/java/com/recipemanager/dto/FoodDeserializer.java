package com.recipemanager.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.recipemanager.model.Food;
import com.recipemanager.model.Unit;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class FoodDeserializer extends JsonDeserializer<Food> {
	@Override
	public Food deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		Long id = null;
		Long unitId = null;
		try {
			id = node.get("id").asLong();
		} catch (Exception e) {
		}
		try {
			unitId = node.get("unitId").asLong();
		} catch (Exception e) {
		}

		return new Food(id, node.get("name").asText(), new Unit(unitId, node.get("unitName").asText()));
	}
}
