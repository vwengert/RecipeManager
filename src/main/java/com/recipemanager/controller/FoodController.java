package com.recipemanager.controller;

import com.recipemanager.dto.FoodDto;
import com.recipemanager.model.Food;
import com.recipemanager.service.FoodService;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class FoodController {
	private final FoodService foodService;
	private static final ModelMapper modelMapper = new ModelMapper();

	@GetMapping(path = "foodById/{foodId}")
	public FoodDto getFoodById(@PathVariable Long foodId) throws NotFoundException {
		return modelMapper.map(foodService.getFoodById(foodId), FoodDto.class);
	}

	@GetMapping(path = "food")
	public List<FoodDto> getFood() {
		return modelMapper.map(
				foodService.getFood(),
				new TypeToken<List<FoodDto>>() {
				}.getType());
	}

	@GetMapping(path = "foodByName/{name}")
	public FoodDto getFoodByName(@PathVariable String name) throws NotFoundException {
		return modelMapper.map(foodService.getFoodByName(name), FoodDto.class);
	}

	@PostMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public FoodDto postFood(@RequestBody FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
		return modelMapper.map(foodService.postFood(modelMapper.map(foodDto, Food.class)), FoodDto.class);
	}

	@PutMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public FoodDto putFood(@RequestBody FoodDto foodDto) throws NotFoundException {
		return modelMapper.map(foodService.putFood(modelMapper.map(foodDto, Food.class)), FoodDto.class);
	}

	@DeleteMapping(path = "food/{foodId}")
	public ResponseEntity<?> deleteFood(@PathVariable Long foodId) throws NoContentException {
		foodService.deleteFood(foodId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
