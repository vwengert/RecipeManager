package com.recipemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/error/")
	public String error() {
		return "error";
	}
}
