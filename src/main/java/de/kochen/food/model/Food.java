package de.kochen.food.model;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Food {
    @Id
    private Long guid;
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unitId")
    private Unit unit;
}
