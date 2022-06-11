package com.recipemanager.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Recipe implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipeHeaderId")
	private RecipeHeader recipeHeader;
	@ManyToOne
	@JoinColumn(name = "foodId")
	private Food food;
	private Double quantity;

}
