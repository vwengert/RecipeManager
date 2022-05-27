package com.recipemanager.service;

import com.recipemanager.model.Unit;
import com.recipemanager.util.FoundException;
import com.recipemanager.util.IdNotAllowedException;
import com.recipemanager.util.NoContentException;
import com.recipemanager.util.NotFoundException;

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
	 * @throws FoundException        when unit not new
	 * @throws IdNotAllowedException id is not allowed on post
	 */
	Unit postUnit(Unit unit) throws FoundException, IdNotAllowedException;

	/**
	 * @param unitName unit Name to search for
	 * @return unit which was searched
	 * @throws NotFoundException unit not found
	 */
	Unit getUnitByName(String unitName) throws NotFoundException;

	/**
	 * @param unit changed unit with its id
	 * @return new saved unit
	 * @throws NotFoundException unit not found
	 */
	Unit putUnit(Unit unit) throws NotFoundException;

	/**
	 * @param id of unit to delete
	 * @throws NoContentException unit not found
	 */
	void delete(Long id) throws NoContentException;
}
