package de.kochen.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
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
