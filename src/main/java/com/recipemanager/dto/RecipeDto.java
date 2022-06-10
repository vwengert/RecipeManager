package com.recipemanager.dto;

import com.recipemanager.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

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

	{
		PropertyMap<Recipe, RecipeDto> recipeDtoPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				map().setRecipeHeaderId(source.getRecipeHeader().getId());
				map().setRecipeHeaderName(source.getRecipeHeader().getName());
				map().setRecipeHeaderDescription(source.getRecipeHeader().getDescription());
				map().setRecipeHeaderPortions(source.getRecipeHeader().getPortions());
				map().setFoodId(source.getFood().getId());
				map().setFoodName(source.getFood().getName());
				map().setFoodUnitId(source.getFood().getUnit().getId());
				map().setFoodUnitName(source.getFood().getUnit().getName());
			}
		};
		PropertyMap<RecipeDto, Recipe> dtoRecipePropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				map().getRecipeHeader().setId(source.getRecipeHeaderId());
				map().getRecipeHeader().setName(source.getRecipeHeaderName());
				map().getRecipeHeader().setDescription(source.getRecipeHeaderDescription());
				map().getRecipeHeader().setPortions(source.getRecipeHeaderPortions());
				map().getFood().setId(source.getFoodId());
				map().getFood().setName(source.getFoodName());
				map().getFood().getUnit().setId(source.getFoodUnitId());
				map().getFood().getUnit().setName(source.getFoodUnitName());
			}
		};
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.addMappings(recipeDtoPropertyMap);
		modelMapper.addMappings(dtoRecipePropertyMap);
	}

	public static RecipeDto getRecipeDto(Recipe recipe) {
		return modelMapper.map(recipe, RecipeDto.class);
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
