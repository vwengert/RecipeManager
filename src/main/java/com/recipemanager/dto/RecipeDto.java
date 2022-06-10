package com.recipemanager.dto;

import com.recipemanager.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto implements Serializable {
	private static final ModelMapper modelMapper = new ModelMapper();
	private Long id;
	private Long RecipeHeaderId;
	private String RecipeHeaderName;
	private String RecipeHeaderDescription;
	private Integer RecipeHeaderPortions;
	private Long FoodId;
	private String FoodName;
	private Long FoodUnitId;
	private String FoodUnitName;
	private Double quantity;

	public static RecipeDto getRecipeDto(Recipe recipe) {
		RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
		return recipeDto;
	}

	public static List<RecipeDto> getRecipeDtoList(List<Recipe> recipeList) {
		return recipeList
				.stream()
				.map(RecipeDto::getRecipeDto)
				.collect(Collectors.toList());
	}

	public Recipe getRecipe() {
		return modelMapper.map(this, Recipe.class);
	}

}
