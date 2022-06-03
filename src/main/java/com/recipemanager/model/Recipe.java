package com.recipemanager.model;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipe_header_id")
	private RecipeHeader recipeHeader;
	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food food;
	private Double quantity;

}
