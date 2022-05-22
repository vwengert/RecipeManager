package de.kochen.food.dto;


import lombok.*;

@Data
public class FoodDto {
    private Long guid;
    private String name;
    private Long unitGuid;
    private String unitName;
}
