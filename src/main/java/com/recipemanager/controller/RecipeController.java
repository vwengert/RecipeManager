package com.recipemanager.controller;

import com.recipemanager.dto.RecipeDto;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeController {
	final private RecipeService recipeService;

	@GetMapping(path = "recipeByRecipeHeaderId/{recipeHeaderId}")
	public ResponseEntity<List<RecipeDto>> getRecipe(@PathVariable Long recipeHeaderId) throws NotFoundException {
		List<Recipe> recipeByRecipeHeaderIdList = recipeService.getRecipeByRecipeHeaderId(recipeHeaderId);
		List<RecipeDto> recipeDtoList = RecipeDto.getRecipeDtoList(recipeByRecipeHeaderIdList);
		return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
	}

}
