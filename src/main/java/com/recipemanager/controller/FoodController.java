package com.recipemanager.controller;

import com.recipemanager.model.Food;
import com.recipemanager.service.FoodService;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
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
	private final FoodService foodService;

	@GetMapping(path = "foodById/{foodId}")
	public Food getFoodById(@PathVariable Long foodId) throws NotFoundException {
		return foodService.getFoodById(foodId);
	}

	@GetMapping(path = "food")
	public List<Food> getFood() {
		return foodService.getFood();
	}

	@GetMapping(path = "foodByName/{name}")
	public Food getFoodByName(@PathVariable String name) throws NotFoundException {
		return foodService.getFoodByName(name);
	}

	@PostMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Food postFood(@RequestBody Food food) throws IdNotAllowedException, NotFoundException, FoundException {
		return foodService.postFood(food);
	}

	@PutMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Food putFood(@RequestBody Food food) throws NotFoundException {
		return foodService.putFood(food);
	}

	@DeleteMapping(path = "food/{foodId}")
	public ResponseEntity<?> deleteFood(@PathVariable Long foodId) throws NoContentException {
		foodService.deleteFood(foodId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
