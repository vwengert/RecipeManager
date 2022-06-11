package com.recipemanager.controller;

import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeController {
	final private RecipeService recipeService;

	@GetMapping(path = "recipeByRecipeHeaderId/{recipeHeaderId}")
	public List<Recipe> getRecipe(@PathVariable Long recipeHeaderId) throws NotFoundException {
		return recipeService.getRecipeByRecipeHeaderId(recipeHeaderId);
	}

	@PutMapping(path = "recipe",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Recipe putRecipe(@RequestBody Recipe recipe) throws NotFoundException, IdNotAllowedException {
		return recipeService.putRecipe(recipe);
	}

}
