package com.recipemanager.controller;

import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
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
public class RecipeController {
	final private RecipeService recipeService;

	@GetMapping(path = "recipe")
	public ResponseEntity<List<Recipe>> getRecipe() {
		return new ResponseEntity<>(recipeService.getRecipe(), HttpStatus.OK);
	}

	@GetMapping(path = "recipeById/{recipeId}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipeId) throws NotFoundException {
		return new ResponseEntity<>(recipeService.getRecipeById(recipeId), HttpStatus.OK);
	}

	@GetMapping(path = "recipeByName/{recipeName}")
	public ResponseEntity<Recipe> getRecipeByName(@PathVariable String recipeName) throws NotFoundException {
		return new ResponseEntity<>(recipeService.getRecipeByName(recipeName), HttpStatus.OK);
	}

	@PostMapping(path = "recipe",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Recipe> postRecipe(@RequestBody Recipe recipe) throws IdNotAllowedException, FoundException {
		return new ResponseEntity<>(recipeService.postRecipe(recipe), HttpStatus.CREATED);
	}

}
