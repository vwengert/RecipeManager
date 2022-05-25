package de.kochen.food.controller;

import de.kochen.food.model.Unit;
import de.kochen.food.service.UnitService;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/")
public class UnitController {
    private final UnitService unitService;

    @GetMapping(path = "unitById/{unitId}")
    public ResponseEntity<Unit> getUnitById(@PathVariable UUID unitId) throws NotFoundException {
        return new ResponseEntity<>(unitService.getUnitById(unitId), HttpStatus.OK);
    }

}
