package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.NotFoundException;

import java.util.List;

public interface UnitService {

	/**
	 * @param unitId Id to search unit
	 * @return Unit
	 * @throws NotFoundException unit not found
	 */
	Unit getUnitById(Long unitId) throws NotFoundException;

	/**
	 * @return Array of units
	 */
	List<Unit> getUnit();

	/**
	 * @param unit new unit
	 * @return saved unit
	 */
	Unit postUnit(Unit unit) throws FoundException;

	Unit getUnitByName(String unitName) throws NotFoundException;

	/**
	 * @param unit changed unit with its id
	 * @return new saved unit
	 */
	Unit putUnit(Unit unit) throws NotFoundException;
}
