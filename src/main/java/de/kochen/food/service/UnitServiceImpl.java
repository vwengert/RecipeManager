package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NoContentException;
import de.kochen.food.util.NotFoundException;
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
