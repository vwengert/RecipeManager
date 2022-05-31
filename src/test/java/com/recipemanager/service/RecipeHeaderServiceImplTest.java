package com.recipemanager.service;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeRepository;
import com.recipemanager.util.annotations.UnitTest;
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

class RecipeHeaderServiceImplTest {
	private final long recipeId = 1L;
	private final int portions = 2;
	private final long recipeNotFoundId = 999L;
	private final RecipeHeader recipeHeaderSuppe = new RecipeHeader(recipeId, "soup", "cook long", portions);
	private final RecipeHeader secondRecipeHeader = new RecipeHeader(recipeId, "salad", "turn on", portions + portions);
	private final List<RecipeHeader> recipeHeaders = List.of(recipeHeaderSuppe, secondRecipeHeader);
	private final RecipeHeader notSavedRecipeHeader = new RecipeHeader(null, "ravioli", "cook", portions + portions + portions);
	RecipeRepository recipeRepository = mock(RecipeRepository.class);
	RecipeHeaderService recipeHeaderService = new RecipeHeaderServiceImpl(recipeRepository);

	@BeforeEach
	public void setUp() {
	}

	@UnitTest
	public void getRecipeByIdReturnsFood() throws NotFoundException {
		when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeHeaderSuppe));

		RecipeHeader recipeHeader = recipeHeaderService.getRecipeHeaderById(recipeId);

		assertNotNull(recipeHeader);
		assertEquals(recipeHeaderSuppe.getName(), recipeHeader.getName());
		assertEquals(recipeHeaderSuppe.getDescription(), recipeHeader.getDescription());
		assertEquals(recipeHeaderSuppe.getPortions(), recipeHeader.getPortions());
	}

	@UnitTest
	public void getRecipeByIdThrowsWhenNotFound() {
		when(recipeRepository.findById(recipeNotFoundId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeHeaderService.getRecipeHeaderById(recipeNotFoundId));
	}

	@UnitTest
	public void getRecipeList() {
		when(recipeRepository.findAll()).thenReturn(recipeHeaders);

		List<RecipeHeader> recipeHeaderList = recipeHeaderService.getRecipeHeader();

		assertNotNull(recipeHeaderList);
		assertEquals(recipeHeaderSuppe.getName(), recipeHeaderList.get(0).getName());
		assertEquals(recipeHeaderSuppe.getPortions(), recipeHeaderList.get(0).getPortions());
		assertEquals(secondRecipeHeader.getId(), recipeHeaderList.get(1).getId());
		assertEquals(secondRecipeHeader.getPortions(), recipeHeaderList.get(1).getPortions());
	}

	@UnitTest
	public void getRecipeByNameReturnsRecipe() throws NotFoundException {
		when(recipeRepository.findByName(recipeHeaderSuppe.getName())).thenReturn(Optional.of(recipeHeaderSuppe));

		RecipeHeader result = recipeHeaderService.getRecipeHeaderByName(recipeHeaderSuppe.getName());

		assertNotNull(result);
		assertEquals(recipeHeaderSuppe.getName(), result.getName());
		assertEquals(recipeHeaderSuppe.getDescription(), result.getDescription());
		assertEquals(recipeHeaderSuppe.getPortions(), result.getPortions());
	}

	@UnitTest
	public void getRecipeByNameThrowsWhenNameNotFound() {

		assertThrows(NotFoundException.class, () -> recipeHeaderService.getRecipeHeaderByName(notSavedRecipeHeader.getName()));
	}

	@UnitTest
	public void postRecipeThrowsIdNotAllowedWhenRequestSendsIds() {
		notSavedRecipeHeader.setId(1L);

		assertThrows(IdNotAllowedException.class, () -> recipeHeaderService.postRecipeHeader(notSavedRecipeHeader));
	}

	@UnitTest
	public void postRecipeThrowsReturns200WhenRecipeNameIsUsed() {
		when(recipeRepository.existsByName(recipeHeaderSuppe.getName())).thenReturn(true);
		recipeHeaderSuppe.setId(null);

		assertThrows(FoundException.class, () -> recipeHeaderService.postRecipeHeader(recipeHeaderSuppe));
	}

	@UnitTest
	public void postRecipeSavesAndReturnsRecipeWhenNameIsNew() throws FoundException, IdNotAllowedException {
		when(recipeRepository.existsByName(any())).thenReturn(false);
		when(recipeRepository.findByName(any())).thenReturn(Optional.empty());
		when(recipeRepository.save(any())).thenReturn(secondRecipeHeader);

		secondRecipeHeader.setId(null);
		RecipeHeader recipeHeader = recipeHeaderService.postRecipeHeader(secondRecipeHeader);
		assertEquals(secondRecipeHeader.getName(), recipeHeader.getName());
		assertEquals(secondRecipeHeader.getDescription(), recipeHeader.getDescription());
		assertEquals(secondRecipeHeader.getPortions(), recipeHeader.getPortions());
	}

	@UnitTest
	public void putRecipeSavesChangedRecipeWhenIdExists() throws NotFoundException {
		RecipeHeader recipeHeaderChanged = new RecipeHeader(recipeHeaderSuppe.getId(), "changed", recipeHeaderSuppe.getDescription(), recipeHeaderSuppe.getPortions());
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipeHeaderSuppe));
		when(recipeRepository.save(any())).thenReturn(recipeHeaderChanged);

		RecipeHeader recipeHeader = recipeHeaderService.putRecipeHeader(recipeHeaderSuppe);
		assertEquals(recipeHeaderSuppe.getId(), recipeHeaderChanged.getId());
		assertEquals(recipeHeaderChanged.getName(), recipeHeader.getName());
		assertEquals(recipeHeaderChanged.getId(), recipeHeader.getId());
	}

	@UnitTest
	public void putRecipeThrowsNotFoundWhenRecipeNotExists() {
		RecipeHeader recipeHeaderChanged = new RecipeHeader(recipeNotFoundId, "changed", recipeHeaderSuppe.getDescription(), recipeHeaderSuppe.getPortions());
		when(recipeRepository.findById(recipeHeaderChanged.getId())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> recipeHeaderService.putRecipeHeader(recipeHeaderSuppe));
	}

	@UnitTest
	public void deleteThrowsNotFoundWhenRecipeNotExists() {
		when(recipeRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NoContentException.class, () -> recipeHeaderService.deleteRecipeHeader(recipeNotFoundId));
	}

	@UnitTest
	public void deleteOnlyDeletesFoodButNotUnit() {
		when(recipeRepository.findById(recipeHeaderSuppe.getId())).thenReturn(Optional.of(recipeHeaderSuppe));

		assertDoesNotThrow(() -> recipeHeaderService.deleteRecipeHeader(recipeHeaderSuppe.getId()));
	}

}