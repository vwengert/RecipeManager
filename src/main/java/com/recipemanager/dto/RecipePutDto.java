package com.recipemanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePutDto implements Serializable {
	private Long id;
	private Long recipeHeaderId;
	@JsonIgnore
	private String recipeHeaderName;
	@JsonIgnore
	private String recipeHeaderDescription;
	@JsonIgnore
	private Integer recipeHeaderPortions;
	private Long foodId;
	@JsonIgnore
	private String foodName;
	@JsonIgnore
	private Long foodUnitId;
	@JsonIgnore
	private String foodUnitName;
	private Double quantity;
}
