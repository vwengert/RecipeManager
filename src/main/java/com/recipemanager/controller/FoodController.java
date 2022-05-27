package com.recipemanager.controller;

import com.recipemanager.dto.FoodDto;
import com.recipemanager.service.FoodGetService;
import com.recipemanager.service.FoodService;
import com.recipemanager.util.FoundException;
import com.recipemanager.util.IdNotAllowedException;
import com.recipemanager.util.NoContentException;
import com.recipemanager.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class FoodController {
	private final FoodGetService foodGetService;
	private final FoodService foodService;

	@GetMapping(path = "foodById/{foodId}")
	public ResponseEntity<FoodDto> getFoodById(@PathVariable Long foodId) throws NotFoundException {
		return new ResponseEntity<>(FoodDto.getFoodDto(foodGetService.getFoodById(foodId)), HttpStatus.OK);
	}

	@GetMapping(path = "food")
	public ResponseEntity<List<FoodDto>> getFood() {
		return new ResponseEntity<>(FoodDto.getFoodDtoList(foodGetService.getFood()), HttpStatus.OK);
	}

	@GetMapping(path = "foodByName/{name}")
	public ResponseEntity<FoodDto> getFoodByName(@PathVariable String name) throws NotFoundException {
		return new ResponseEntity<>(FoodDto.getFoodDto(foodGetService.getFoodByName(name)), HttpStatus.OK);
	}

	@PostMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FoodDto> postFood(@RequestBody FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
		return new ResponseEntity<>(FoodDto.getFoodDto(foodService.postFood(foodDto.getFood())), HttpStatus.CREATED);
	}

	@PutMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FoodDto> putFood(@RequestBody FoodDto foodDto) throws NotFoundException {
		return new ResponseEntity<>(FoodDto.getFoodDto(foodService.putFood(foodDto.getFood())), HttpStatus.OK);
	}

	@DeleteMapping(path = "food/{foodId}")
	public ResponseEntity<?> deleteFood(@PathVariable Long foodId) throws NoContentException {
		foodService.deleteFood(foodId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
