package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UnitServiceImplTest {
    UnitRepository unitRepository = mock(UnitRepository.class);
    UnitServiceImpl unitService = new UnitServiceImpl(unitRepository);

    @UnitTest
    public void getUnitByIdReturnsFood() throws NotFoundException {
        when(unitRepository.findById(new UUID(1, 1))).thenReturn(Optional.of(new Unit(new UUID(1, 1), "piece")));

        Unit unit = unitService.getUnitById(new UUID(1, 1));

        assertEquals("piece", unit.getName());
    }

    @UnitTest
    public void getUnitByIdThrowsWhenNotFound() {
        when(unitRepository.findById(new UUID(3, 3))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> unitService.getUnitById(new UUID(3, 3)));
    }

}