package com.recipemanager.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.recipemanager.model.Food;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.model.Unit;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RecipeDeserializer extends JsonDeserializer<Recipe> {
	@Override
	public Recipe deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		Long id = null;
		try {
			id = node.get("recipeHeaderId").asLong();
		} catch (Exception e) {
		}
		Integer portions = null;
		try {
			portions = node.get("recipeHeaderPortions").asInt();
		} catch (Exception e) {
		}
		RecipeHeader recipeHeader = new RecipeHeader(
				id,
				node.get("recipeHeaderName").asText(),
				node.get("recipeHeaderDescription").asText(),
				portions);

		id = null;
		try {
			id = node.get("foodUnitId").asLong();
		} catch (Exception e) {
		}
		Unit unit = new Unit(
				id,
				node.get("foodUnitName").asText());

		id = null;
		try {
			id = node.get("foodId").asLong();
		} catch (Exception e) {
		}
		Food food = new Food(
				id,
				node.get("foodName").asText(),
				unit);

		id = null;
		try {
			id = node.get("id").asLong();
		} catch (Exception e) {
		}
		Double quantity = null;
		try {
			quantity = node.get("quantity").asDouble();
		} catch (Exception e) {
		}

		return new Recipe(
				id,
				recipeHeader,
				food,
				quantity);
	}
}
