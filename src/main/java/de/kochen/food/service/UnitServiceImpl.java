package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    @Override
    public Unit getUnitById(Long unitId) throws NotFoundException {
        return unitRepository.findById(unitId).orElseThrow(
                NotFoundException::new
        );
    }

    @Override
    public Unit postUnit(Unit unit) throws FoundException {
        if (unitRepository.existsByName(unit.getName()))
            throw new FoundException();
        return unitRepository.save(unit);
    }
}
