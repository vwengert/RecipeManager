package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.util.NotFoundException;

public interface UnitService {
    Unit getUnitById(Long unitId) throws NotFoundException;
}
