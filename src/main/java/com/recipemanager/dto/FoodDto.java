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
public class FoodDto implements Serializable {
	private Long id;
	private String name;
	private Long unitId;
	private String unitName;
}
