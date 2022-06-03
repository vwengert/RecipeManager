package com.recipemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class RecipeController {

	@GetMapping(path = "recipe")
	public ResponseEntity<String> getRecipe() {
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
