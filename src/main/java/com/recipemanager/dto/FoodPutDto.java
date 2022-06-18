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
public class FoodPutDto implements Serializable {
	private Long id;
	private String name;
	private Long unitId;
	@JsonIgnore
	private String unitName;
}
