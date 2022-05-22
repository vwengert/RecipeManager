package de.kochen.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Unit {
    @Id
    private Long guid;
    private String name;
}
