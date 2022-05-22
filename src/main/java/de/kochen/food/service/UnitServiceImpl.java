package de.kochen.food.service;

import de.kochen.food.model.Unit;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {
    @Override
    public Unit getUnitById(Long unitId) {
        return new Unit(1L, "Stück");
    }
}
