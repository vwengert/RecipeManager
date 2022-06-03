package com.recipemanager.controller;

import com.recipemanager.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeController {
	final private RecipeService recipeService;

	@GetMapping(path = "recipeById/{recipeId}")
	public ResponseEntity<String> getRecipe(@PathVariable Long recipeId) {
		return new ResponseEntity<>("[{\"recipeHeaderName\":\"Suppe\"," +
				"\"foodName\":\"Kartoffel\"," +
				"\"Quantity\":3}]", HttpStatus.OK);
	}

}
