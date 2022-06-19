package com.recipemanager.controller;

import com.recipemanager.dto.RecipeDto;
import com.recipemanager.dto.RecipePostDto;
import com.recipemanager.dto.RecipePutDto;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
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
public class RecipeController {
	final private RecipeService recipeService;
	private final ModelMapper modelMapper = new ModelMapper();

	@Operation(summary = "get recipe items by recipe header id", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = RecipeDto.class)))),
			@ApiResponse(description = "Recipe items not found", responseCode = "404", content = @Content)
	})
	@GetMapping(path = "recipeByRecipeHeaderId/{recipeHeaderId}")
	public List<RecipeDto> getRecipe(@PathVariable Long recipeHeaderId) throws NotFoundException {
		return modelMapper.map(
				recipeService.getRecipeByRecipeHeaderId(recipeHeaderId),
				new TypeToken<List<RecipeDto>>() {
				}.getType());
	}

	@Operation(summary = "Post a new recipe item", responses = {
			@ApiResponse(description = "Recipe created", responseCode = "201",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))),
			@ApiResponse(description = "Recipe found", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))),
			@ApiResponse(description = "Id not allowed", responseCode = "406",
					content = @Content),
	})
	@PostMapping(path = "recipe",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RecipeDto postRecipe(
			@Parameter(
					description = "Recipe header and food id and quantity",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			@RequestBody RecipePostDto recipePostDto) throws NotFoundException, IdNotAllowedException {
		return modelMapper.map(recipeService.postRecipe(modelMapper.map(recipePostDto, Recipe.class)), RecipeDto.class);
	}

	@Operation(summary = "Change a recipe item", responses = {
			@ApiResponse(description = "Recipe item changed", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))),
			@ApiResponse(description = "Id to change not found", responseCode = "404",
					content = @Content),
	})
	@PutMapping(path = "recipe",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public RecipeDto putRecipe(
			@Parameter(
					description = "Recipe header and food id and quantity of recipe to to change",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			@RequestBody RecipePutDto recipePutDto) throws NotFoundException {
		return modelMapper.map(recipeService.putRecipe(modelMapper.map(recipePutDto, Recipe.class)), RecipeDto.class);
	}

	@Operation(summary = "Delete a recipe item", responses = {
			@ApiResponse(description = "recipe item deleted", responseCode = "200", content = @Content),
			@ApiResponse(description = "Id to delete not found", responseCode = "204", content = @Content),
	})
	@DeleteMapping(path = "recipe/{recipeId}")
	public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) throws NoContentException {
		recipeService.delete(recipeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}