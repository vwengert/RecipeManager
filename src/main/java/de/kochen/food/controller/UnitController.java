package de.kochen.food.controller;

import de.kochen.food.model.Unit;
import de.kochen.food.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/unit")
public class UnitController {
    private final UnitService unitService;

    @GetMapping(path = "{unitId}")
    public Unit getUnitById(@PathVariable Long unitId) {
        return unitService.getUnitById(unitId);
    }

}
