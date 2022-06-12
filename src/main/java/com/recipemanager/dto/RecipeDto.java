package com.recipemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto implements Serializable {
	private Long id;
	private Long recipeHeaderId;
	private String recipeHeaderName;
	private String recipeHeaderDescription;
	private Integer recipeHeaderPortions;
	private Long foodId;
	private String foodName;
	private Long foodUnitId;
	private String foodUnitName;
	private Double quantity;
}
