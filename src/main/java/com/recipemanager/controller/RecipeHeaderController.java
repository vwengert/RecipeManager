package com.recipemanager.controller;

import com.recipemanager.dto.RecipeHeaderPostDto;
import com.recipemanager.model.RecipeHeader;
import com.recipemanager.service.RecipeHeaderService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeHeaderController {
	private final RecipeHeaderService recipeHeaderService;
	private final ModelMapper modelMapper = new ModelMapper();

	@Operation(summary = "get recipe headers", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = RecipeHeader.class)))),
	})
	@GetMapping(path = "recipeHeader")
	public ResponseEntity<List<RecipeHeader>> getRecipeHeader() {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeader(), HttpStatus.OK);
	}

	@Operation(summary = "Get recipe header by id", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeHeader.class))),
			@ApiResponse(description = "Recipe header not found", responseCode = "404", content = @Content),
	})
	@GetMapping(path = "recipeHeaderById/{recipeId}")
	public ResponseEntity<RecipeHeader> getRecipeHeaderById(@PathVariable Long recipeId) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeaderById(recipeId), HttpStatus.OK);
	}

	@Operation(summary = "Get recipe header by name", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeHeader.class))),
			@ApiResponse(description = "Recipe header not found", responseCode = "404", content = @Content),
	})
	@GetMapping(path = "recipeHeaderByName/{recipeName}")
	public ResponseEntity<RecipeHeader> getRecipeHeaderByName(@PathVariable String recipeName) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeaderByName(recipeName), HttpStatus.OK);
	}

	@Operation(summary = "Post a new recipe header", responses = {
			@ApiResponse(description = "Recipe header created", responseCode = "201",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeHeader.class))),
			@ApiResponse(description = "Recipe Header found", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeHeader.class))),
			@ApiResponse(description = "Id not allowed", responseCode = "406",
					content = @Content),
	})
	@PostMapping(path = "recipeHeader",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecipeHeader> postRecipeHeader(
			@RequestBody
			@Parameter(
					description = "Name, description and portions of the new recipe header",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			RecipeHeaderPostDto recipeHeaderPostDto) throws IdNotAllowedException, FoundException {
		return new ResponseEntity<>(recipeHeaderService.postRecipeHeader(modelMapper.map(recipeHeaderPostDto, RecipeHeader.class)), HttpStatus.CREATED);
	}

	@Operation(summary = "Change a recipe header", responses = {
			@ApiResponse(description = "Recipe header changed", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeHeader.class))),
			@ApiResponse(description = "Id to change not found", responseCode = "404",
					content = @Content),
	})
	@PutMapping(path = "recipeHeader",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecipeHeader> putRecipeHeader(@RequestBody RecipeHeader recipeHeader) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.putRecipeHeader(recipeHeader), HttpStatus.OK);
	}

	@Operation(summary = "Delete a recipe header", responses = {
			@ApiResponse(description = "Recipe header deleted", responseCode = "200", content = @Content),
			@ApiResponse(description = "Id to delete not found", responseCode = "204", content = @Content),
	})
	@DeleteMapping(path = "recipeHeader/{recipeId}")
	public ResponseEntity<?> deleteRecipeHeader(@PathVariable Long recipeId) throws NoContentException {
		recipeHeaderService.deleteRecipeHeader(recipeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
