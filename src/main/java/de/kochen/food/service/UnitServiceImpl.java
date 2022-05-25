package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    @Override
    public Unit getUnitById(UUID unitId) throws NotFoundException {
        return unitRepository.findById(unitId).orElseThrow(
                NotFoundException::new
        );
    }
}
