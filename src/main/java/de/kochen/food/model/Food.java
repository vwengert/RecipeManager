package de.kochen.food.model;

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
public class Food implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitId")
	private Unit unit;
}
