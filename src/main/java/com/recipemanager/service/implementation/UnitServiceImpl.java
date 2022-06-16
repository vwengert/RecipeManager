package com.recipemanager.service.implementation;

import com.recipemanager.model.Unit;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.service.UnitService;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UnitServiceImpl implements UnitService {
	private final UnitRepository unitRepository;

	@Override
	public Unit getUnitById(Long unitId) throws NotFoundException {
		return unitRepository.findById(unitId).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public Unit getUnitByName(String unitName) throws NotFoundException {
		return unitRepository.findByName(unitName).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public List<Unit> getUnit() {
		return unitRepository.findAll();
	}

	@Override
	public Unit postUnit(Unit unit) throws FoundException, IdNotAllowedException {
		if (unit.getId() != null)
			throw new IdNotAllowedException();
		if (unitRepository.existsByName(unit.getName()))
			throw new FoundException();
		return unitRepository.save(unit);
	}

	@Override
	public Unit putUnit(Unit unit) throws NotFoundException {
		Unit searchedUnit = unitRepository.findById(unit.getId()).orElseThrow(
				NotFoundException::new);

		searchedUnit.setName(unit.getName());
		return unitRepository.save(searchedUnit);
	}

	@Override
	public void delete(Long id) throws NoContentException {
		Unit unit = unitRepository.findById(id).orElseThrow(
				NoContentException::new);

		unitRepository.delete(unit);
	}

}
