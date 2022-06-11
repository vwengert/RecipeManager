package com.recipemanager.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.recipemanager.model.Recipe;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RecipeSerializer extends JsonSerializer<Recipe> {

	@Override
	public void serialize(Recipe recipe, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", recipe.getId());
		gen.writeObjectField("recipeHeaderId", recipe.getRecipeHeader().getId());
		gen.writeObjectField("recipeHeaderName", recipe.getRecipeHeader().getName());
		gen.writeObjectField("recipeHeaderDescription", recipe.getRecipeHeader().getDescription());
		gen.writeObjectField("recipeHeaderPortions", recipe.getRecipeHeader().getPortions());
		gen.writeObjectField("foodId", recipe.getFood().getId());
		gen.writeObjectField("foodName", recipe.getFood().getName());
		gen.writeObjectField("foodUnitId", recipe.getFood().getUnit().getId());
		gen.writeObjectField("foodUnitName", recipe.getFood().getUnit().getName());
		gen.writeObjectField("quantity", recipe.getQuantity());
		gen.writeEndObject();

	}
}
