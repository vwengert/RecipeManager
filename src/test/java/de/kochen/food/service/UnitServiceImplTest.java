package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UnitServiceImplTest {
    UnitRepository unitRepository = mock(UnitRepository.class);
    UnitServiceImpl unitService = new UnitServiceImpl(unitRepository);

    @UnitTest
    public void getUnitByIdReturnsFood() throws NotFoundException {
        when(unitRepository.findById(1L)).thenReturn(Optional.of(new Unit(1L, "piece")));

        Unit unit = unitService.getUnitById(1L);

        assertEquals("piece", unit.getName());
    }

    @UnitTest
    public void getUnitByIdThrowsWhenNotFound() {
        when(unitRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> unitService.getUnitById(3L));
    }

}