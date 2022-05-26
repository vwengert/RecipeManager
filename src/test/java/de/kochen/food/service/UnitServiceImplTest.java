package de.kochen.food.service;

import de.kochen.food.model.Unit;
import de.kochen.food.repository.UnitRepository;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.NotFoundException;
import de.kochen.food.util.UnitTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UnitServiceImplTest {
	UnitRepository unitRepository = mock(UnitRepository.class);
	UnitServiceImpl unitService = new UnitServiceImpl(unitRepository);

	private List<Unit> unitList;

	@BeforeEach
	public void setUp() {
		unitList = List.of(
				Unit.builder().id(1L).name("piece").build(),
				Unit.builder().id(2L).name("kg").build()
		);
	}

	@UnitTest
	public void getUnitByIdReturnsFood() throws NotFoundException {
		when(unitRepository.findById(1L)).thenReturn(Optional.of(new Unit(1L, unitList.get(0).getName())));

		Unit unit = unitService.getUnitById(1L);

		assertEquals(unitList.get(0).getName(), unit.getName());
	}

	@UnitTest
	public void getUnitByIdThrowsWhenNotFound() {
		when(unitRepository.findById(3L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> unitService.getUnitById(3L));
	}

	@UnitTest
	public void getUnitReturnsArray() {
		when(unitRepository.findAll()).thenReturn(unitList);

		List<Unit> units = unitService.getUnit();

		assertNotNull(units);
		assertEquals("piece", units.get(0).getName());
		assertEquals("kg", units.get(1).getName());
	}

	@UnitTest
	public void postUnitReturnsUnitWhenCreated() throws FoundException {
		when(unitRepository.existsByName(any())).thenReturn(false);
		when(unitRepository.save(any())).thenReturn(unitList.get(0));

		Unit unit = unitService.postUnit(new Unit(null, unitList.get(0).getName()));

		assertEquals(unitList.get(0).getName(), unit.getName());
		assertNotNull(unit.getId());
	}

	@UnitTest
	public void postUnitThrows200WhenUnitAlreadyExists() {
		when(unitRepository.existsByName(any())).thenReturn(true);

		assertThrows(FoundException.class, () -> unitService.postUnit(new Unit(null, unitList.get(0).getName())));
	}

}