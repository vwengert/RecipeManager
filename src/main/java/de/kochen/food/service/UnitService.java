package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.NotFoundException;

public interface UnitService {

    /**
     * @param unitId Id to search for Unit
     * @return Unit
     * @throws NotFoundException Unit not found
     */
    Unit getUnitById(Long unitId) throws NotFoundException;

    /**
     * @param unit neue anzulegende Unit
     * @return neu angelegte Unit
     */
    Unit postUnit(Unit unit) throws FoundException;
}
