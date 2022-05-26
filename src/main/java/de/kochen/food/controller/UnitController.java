package de.kochen.food.controller;

import de.kochen.food.model.Unit;
import de.kochen.food.service.UnitService;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.NotFoundException;
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
	public ResponseEntity<Unit> postUnit(@RequestBody Unit unit) throws FoundException {
		return new ResponseEntity<>(unitService.postUnit(unit), HttpStatus.CREATED);
	}

	@PutMapping(path = "unit",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Unit> putUnit(@RequestBody Unit unit) throws NotFoundException {
		return new ResponseEntity<>(unitService.putUnit(unit), HttpStatus.OK);
	}

}
