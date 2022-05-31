package com.recipemanager.controller;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.service.RecipeHeaderService;
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
public class RecipeHeaderController {
	final private RecipeHeaderService recipeHeaderService;

	@GetMapping(path = "recipeHeader")
	public ResponseEntity<List<RecipeHeader>> getRecipe() {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeader(), HttpStatus.OK);
	}

	@GetMapping(path = "recipeHeaderById/{recipeId}")
	public ResponseEntity<RecipeHeader> getRecipeById(@PathVariable Long recipeId) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeaderById(recipeId), HttpStatus.OK);
	}

	@GetMapping(path = "recipeHeaderByName/{recipeName}")
	public ResponseEntity<RecipeHeader> getRecipeByName(@PathVariable String recipeName) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.getRecipeHeaderByName(recipeName), HttpStatus.OK);
	}

	@PostMapping(path = "recipeHeader",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecipeHeader> postRecipe(@RequestBody RecipeHeader recipeHeader) throws IdNotAllowedException, FoundException {
		return new ResponseEntity<>(recipeHeaderService.postRecipeHeader(recipeHeader), HttpStatus.CREATED);
	}

	@PutMapping(path = "recipeHeader",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecipeHeader> putRecipe(@RequestBody RecipeHeader recipeHeader) throws NotFoundException {
		return new ResponseEntity<>(recipeHeaderService.putRecipeHeader(recipeHeader), HttpStatus.OK);
	}

	@DeleteMapping(path = "recipeHeader/{recipeId}")
	public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) throws NoContentException {
		recipeHeaderService.deleteRecipeHeader(recipeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
