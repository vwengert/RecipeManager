package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.util.NotFoundException;

import java.util.UUID;

public interface UnitService {

    /**
     * @param unitId Id to search for Unit
     * @return Unit
     * @throws NotFoundException Unit not found
     */
    Unit getUnitById(UUID unitId) throws NotFoundException;
}
