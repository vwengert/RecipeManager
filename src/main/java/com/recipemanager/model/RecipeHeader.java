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
public class RecipeHeader implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private Integer portions;
}
