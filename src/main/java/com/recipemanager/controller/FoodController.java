package com.recipemanager.controller;

import com.recipemanager.dto.FoodDto;
import com.recipemanager.dto.FoodPostDto;
import com.recipemanager.dto.FoodPutDto;
import com.recipemanager.model.Food;
import com.recipemanager.service.FoodService;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	private final ModelMapper modelMapper = new ModelMapper();

	@Operation(summary = "get food by id", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
			@ApiResponse(description = "Food not found", responseCode = "404", content = @Content)
	})
	@GetMapping(path = "foodById/{foodId}")
	public FoodDto getFoodById(@PathVariable Long foodId) throws NotFoundException {
		return modelMapper.map(foodService.getFoodById(foodId), FoodDto.class);
	}


	@Operation(summary = "get food", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = FoodDto.class)))),
	})
	@GetMapping(path = "food")
	public List<FoodDto> getFood() {
		return modelMapper.map(foodService.getFood(), new TypeToken<List<FoodDto>>() {
		}.getType());
	}

	@Operation(summary = "get food by name", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
			@ApiResponse(description = "Food not found", responseCode = "404", content = @Content)
	})
	@GetMapping(path = "foodByName/{name}")
	public FoodDto getFoodByName(@PathVariable String name) throws NotFoundException {
		return modelMapper.map(foodService.getFoodByName(name), FoodDto.class);
	}

	@Operation(summary = "Post a new food", responses = {
			@ApiResponse(description = "Food created", responseCode = "201",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
			@ApiResponse(description = "Food found", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
			@ApiResponse(description = "Id not allowed", responseCode = "406",
					content = @Content),
	})
	@PostMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public FoodDto postFood(
			@Parameter(
					description = "Name and unit id of the new food",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			@RequestBody FoodPostDto foodPostDto) throws IdNotAllowedException, NotFoundException, FoundException {
		return modelMapper.map(foodService.postFood(modelMapper.map(foodPostDto, Food.class)), FoodDto.class);
	}

	@Operation(summary = "Change a food", responses = {
			@ApiResponse(description = "Food changed", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
			@ApiResponse(description = "Id to change not found", responseCode = "404",
					content = @Content),
	})
	@PutMapping(path = "food",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public FoodDto putFood(
			@Parameter(
					description = "Food id and name and unit id of food to change",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			@RequestBody FoodPutDto foodPutDto) throws NotFoundException {
		return modelMapper.map(foodService.putFood(modelMapper.map(foodPutDto, Food.class)), FoodDto.class);
	}

	@Operation(summary = "Delete a food", responses = {
			@ApiResponse(description = "Food deleted", responseCode = "200", content = @Content),
			@ApiResponse(description = "Id to delete not found", responseCode = "204", content = @Content),
	})
	@DeleteMapping(path = "food/{foodId}")
	public ResponseEntity<?> deleteFood(@PathVariable Long foodId) throws NoContentException {
		foodService.deleteFood(foodId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
