package de.kochen.food.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodDto {
    private Long guid;
    private String name;
    private Long unitGuid;
    private String unitName;
}
