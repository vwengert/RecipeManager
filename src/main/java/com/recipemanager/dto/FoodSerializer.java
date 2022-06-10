package com.recipemanager.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.recipemanager.model.Food;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class FoodSerializer extends JsonSerializer<Food> {


	@Override
	public void serialize(Food food, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", food.getId());
		gen.writeObjectField("name", food.getName());
		gen.writeObjectField("unitId", food.getUnit().getId());
		gen.writeObjectField("unitName", food.getUnit().getName());
		gen.writeEndObject();

	}

}

