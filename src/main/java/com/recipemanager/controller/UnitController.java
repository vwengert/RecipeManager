package com.recipemanager.controller;

import com.recipemanager.model.Unit;
import com.recipemanager.service.UnitService;
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
@RequestMapping(path = "api/v1/")
public class UnitController {
	private final UnitService unitService;

	@GetMapping(path = "unitById/{unitId}")
	public ResponseEntity<Unit> getUnitById(@PathVariable Long unitId) throws NotFoundException {
		return new ResponseEntity<>(unitService.getUnitById(unitId), HttpStatus.OK);
	}

	@GetMapping(path = "unitByName/{unitName}")
	public ResponseEntity<Unit> getUnitByName(@PathVariable String unitName) throws NotFoundException {
		return new ResponseEntity<>(unitService.getUnitByName(unitName), HttpStatus.OK);
	}

	@GetMapping(path = "unit")
	public ResponseEntity<List<Unit>> getUnit() {
		return new ResponseEntity<>(unitService.getUnit(), HttpStatus.OK);
	}

	@PostMapping(path = "unit",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Unit> postUnit(@RequestBody Unit unit) throws FoundException, IdNotAllowedException {
		return new ResponseEntity<>(unitService.postUnit(unit), HttpStatus.CREATED);
	}

	@PutMapping(path = "unit",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Unit> putUnit(@RequestBody Unit unit) throws NotFoundException {
		return new ResponseEntity<>(unitService.putUnit(unit), HttpStatus.OK);
	}

	@DeleteMapping(path = "unit/{unitId}")
	public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) throws NoContentException {
		unitService.delete(unitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
