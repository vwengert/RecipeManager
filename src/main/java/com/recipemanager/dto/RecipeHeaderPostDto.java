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
public class RecipeHeaderPostDto implements Serializable {
	private String name;
	private String description;
	private Integer portions;
}
