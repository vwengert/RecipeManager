package com.recipemanager.controller;

import com.recipemanager.dto.RecipeDto;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeController {
	final private RecipeService recipeService;
	private static final ModelMapper modelMapper = new ModelMapper();

	@GetMapping(path = "recipeByRecipeHeaderId/{recipeHeaderId}")
	public List<RecipeDto> getRecipe(@PathVariable Long recipeHeaderId) throws NotFoundException {
		return modelMapper.map(
				recipeService.getRecipeByRecipeHeaderId(recipeHeaderId),
				new TypeToken<List<RecipeDto>>() {
				}.getType());
	}

	@PostMapping(path = "recipe",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RecipeDto postRecipe(@RequestBody RecipeDto recipeDto) throws NotFoundException, IdNotAllowedException {
		return modelMapper.map(recipeService.postRecipe(modelMapper.map(recipeDto, Recipe.class)), RecipeDto.class);
	}

}
