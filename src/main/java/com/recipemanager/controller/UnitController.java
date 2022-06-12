package com.recipemanager.controller;

import com.recipemanager.dto.UnitPostDto;
import com.recipemanager.model.Unit;
import com.recipemanager.service.UnitService;
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
@RequestMapping(path = "api/v1/")
public class UnitController {
	private final UnitService unitService;
	private final ModelMapper modelMapper = new ModelMapper();

	@Operation(summary = "Get unit by id", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
			@ApiResponse(description = "Unit not found", responseCode = "404", content = @Content),
	})
	@GetMapping(path = "unitById/{unitId}")
	public ResponseEntity<Unit> getUnitById(@PathVariable Long unitId) throws NotFoundException {
		return new ResponseEntity<>(unitService.getUnitById(unitId), HttpStatus.OK);
	}

	@Operation(summary = "Get unit by name", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
			@ApiResponse(description = "Unit not found", responseCode = "404", content = @Content),
	})
	@GetMapping(path = "unitByName/{unitName}")
	public ResponseEntity<Unit> getUnitByName(@PathVariable String unitName) throws NotFoundException {
		return new ResponseEntity<>(unitService.getUnitByName(unitName), HttpStatus.OK);
	}

	@Operation(summary = "Get units", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200",
					content = @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = Unit.class)))),
	})
	@GetMapping(path = "unit")
	public ResponseEntity<List<Unit>> getUnit() {
		return new ResponseEntity<>(unitService.getUnit(), HttpStatus.OK);
	}

	@Operation(summary = "Post a new unit", responses = {
			@ApiResponse(description = "Unit created", responseCode = "201",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
			@ApiResponse(description = "Unit found", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
			@ApiResponse(description = "Id not allowed", responseCode = "406",
					content = @Content),
	})
	@PostMapping(path = "unit",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Unit> postUnit(
			@RequestBody
			@Parameter(
					description = "Name of the new Unit",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			UnitPostDto unitPostDto) throws FoundException, IdNotAllowedException {
		return new ResponseEntity<>(unitService.postUnit(modelMapper.map(unitPostDto, Unit.class)), HttpStatus.CREATED);
	}

	@Operation(summary = "Change a unit", responses = {
			@ApiResponse(description = "Unit changed", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
			@ApiResponse(description = "Id to change not found", responseCode = "404",
					content = @Content),
	})
	@PutMapping(path = "unit",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Unit> putUnit(
			@RequestBody
			@Parameter(
					description = "Unit to change",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			Unit unit) throws NotFoundException {
		return new ResponseEntity<>(unitService.putUnit(unit), HttpStatus.OK);
	}

	@Operation(summary = "Delete a unit", responses = {
			@ApiResponse(description = "Unit deleted", responseCode = "200", content = @Content),
			@ApiResponse(description = "Id to delete not found", responseCode = "204", content = @Content),
	})
	@DeleteMapping(path = "unit/{unitId}")
	public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) throws NoContentException {
		unitService.delete(unitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
