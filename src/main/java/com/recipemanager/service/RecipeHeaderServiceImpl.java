package com.recipemanager.service;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.exceptions.FoundException;
import com.recipemanager.util.exceptions.IdNotAllowedException;
import com.recipemanager.util.exceptions.NoContentException;
import com.recipemanager.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeHeaderServiceImpl implements RecipeHeaderService {
	private final RecipeHeaderRepository recipeHEaderRepository;

	@Override
	public RecipeHeader getRecipeHeaderById(Long recipeId) throws NotFoundException {
		return recipeHEaderRepository.findById(recipeId).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public List<RecipeHeader> getRecipeHeader() {
		return recipeHEaderRepository.findAll();
	}

	@Override
	public RecipeHeader getRecipeHeaderByName(String recipeName) throws NotFoundException {
		return recipeHEaderRepository.findByName(recipeName).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public RecipeHeader postRecipeHeader(RecipeHeader recipeHeader) throws IdNotAllowedException, FoundException {
		if (recipeHeader.getId() != null)
			throw new IdNotAllowedException();
		if (recipeHEaderRepository.existsByName(recipeHeader.getName()))
			throw new FoundException();
		return recipeHEaderRepository.save(recipeHeader);
	}

	@Override
	public RecipeHeader putRecipeHeader(RecipeHeader recipeHeader) throws NotFoundException {
		RecipeHeader oldRecipeHeader = recipeHEaderRepository.findById(recipeHeader.getId()).orElseThrow(
				NotFoundException::new
		);
		oldRecipeHeader.setName(recipeHeader.getName());
		oldRecipeHeader.setDescription(recipeHeader.getDescription());
		oldRecipeHeader.setPortions(recipeHeader.getPortions());

		return recipeHEaderRepository.save(oldRecipeHeader);
	}

	@Override
	public void deleteRecipeHeader(Long recipeId) throws NoContentException {
		RecipeHeader recipeHeaderToDelete = recipeHEaderRepository.findById(recipeId).orElseThrow(
				NoContentException::new);
		recipeHEaderRepository.delete(recipeHeaderToDelete);
	}
}
