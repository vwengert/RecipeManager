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
public class RecipeHeader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private Integer portions;
}
