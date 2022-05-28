package com.recipemanager.service;

import com.recipemanager.model.Unit;
import com.recipemanager.repository.UnitRepository;
import com.recipemanager.util.*;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
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
	public void getUnitByIdReturnsUnit() throws NotFoundException {
		when(unitRepository.findById(1L)).thenReturn(Optional.of(unitList.get(0)));

		Unit unit = unitService.getUnitById(1L);

		assertNotNull(unit);
		assertEquals(unitList.get(0).getName(), unit.getName());
	}

	@UnitTest
	public void getUnitByIdThrowsWhenNotFound() {
		when(unitRepository.findById(3L)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> unitService.getUnitById(3L));
	}

	@UnitTest
	public void getUnitByNameThrowsWhenNotFound() {
		when(unitRepository.findByName(any())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> unitService.getUnitByName("nothing"));
	}

	@UnitTest
	public void getUnitByNameReturnsUnit() throws NotFoundException {
		when(unitRepository.findByName(any())).thenReturn(Optional.of(unitList.get(0)));

		Unit unit = unitService.getUnitByName(unitList.get(0).getName());

		assertNotNull(unit);
		assertEquals(unitList.get(0).getName(), unit.getName());
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
	public void postUnitReturnsUnitWhenCreated() throws FoundException, IdNotAllowedException {
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

	@UnitTest
	public void putUnitReturnsChangedUnit() throws NotFoundException {
		when(unitRepository.findById(any())).thenReturn(Optional.of(unitList.get(0)));
		when(unitRepository.save(any())).thenReturn(new Unit(unitList.get(0).getId(), unitList.get(1).getName()));

		Unit unit = unitService.putUnit(new Unit(unitList.get(0).getId(), unitList.get(1).getName()));

		assertNotNull(unit);
		assertEquals(unitList.get(0).getId(), unit.getId());
		assertEquals(unitList.get(1).getName(), unit.getName());
	}

	@UnitTest
	public void putUnitThrowsNotFoundWhenIdNotExist() {
		when(unitRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> unitService.putUnit(new Unit(77L, "please change")));
	}

	@UnitTest
	public void deleteUnitReturnsTrue() {
		Long id = unitList.get(0).getId();
		when(unitRepository.findById(id)).thenReturn(Optional.of(new Unit(id, "delete me")));

		assertDoesNotThrow(() -> unitService.delete(id));
	}

	@UnitTest
	public void deleteThrowsNotFoundWhenNothingToDelete() {
		Long id = 9999L;
		when(unitRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NoContentException.class, () -> unitService.delete(id));
	}

}