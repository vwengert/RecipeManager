package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.util.NotFoundException;

public interface UnitService {

    /**
     * @param unitId                Id to search for Unit
     * @return                      Unit
     * @throws NotFoundException    Unit not found
     */
    Unit getUnitById(Long unitId) throws NotFoundException;
}
