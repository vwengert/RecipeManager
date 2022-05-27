package de.kochen.food.dto;

import de.kochen.food.model.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto implements Serializable {
	private static final ModelMapper modelMapper = new ModelMapper();
	private Long id;
	private String name;
	private Long unitId;
	private String unitName;

	public static FoodDto getFoodDto(Food food) {
		return modelMapper.map(food, FoodDto.class);
	}

	public static List<FoodDto> getFoodDtoList(List<Food> foodList) {
		return foodList
				.stream()
				.map(FoodDto::getFoodDto)
				.collect(Collectors.toList());
	}

	public Food getFood() {
		return modelMapper.map(this, Food.class);
	}

}
