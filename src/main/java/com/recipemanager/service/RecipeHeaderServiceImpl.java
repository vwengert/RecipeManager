package com.recipemanager.service;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeRepository;
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
	private final RecipeRepository recipeRepository;

	@Override
	public RecipeHeader getRecipeHeaderById(Long recipeId) throws NotFoundException {
		return recipeRepository.findById(recipeId).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public List<RecipeHeader> getRecipeHeader() {
		return recipeRepository.findAll();
	}

	@Override
	public RecipeHeader getRecipeHeaderByName(String recipeName) throws NotFoundException {
		return recipeRepository.findByName(recipeName).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public RecipeHeader postRecipeHeader(RecipeHeader recipeHeader) throws IdNotAllowedException, FoundException {
		if (recipeHeader.getId() != null)
			throw new IdNotAllowedException();
		if (recipeRepository.existsByName(recipeHeader.getName()))
			throw new FoundException();
		return recipeRepository.save(recipeHeader);
	}

	@Override
	public RecipeHeader putRecipeHeader(RecipeHeader recipeHeader) throws NotFoundException {
		RecipeHeader oldRecipeHeader = recipeRepository.findById(recipeHeader.getId()).orElseThrow(
				NotFoundException::new
		);
		oldRecipeHeader.setName(recipeHeader.getName());
		oldRecipeHeader.setDescription(recipeHeader.getDescription());
		oldRecipeHeader.setPortions(recipeHeader.getPortions());

		return recipeRepository.save(oldRecipeHeader);
	}

	@Override
	public void deleteRecipeHeader(Long recipeId) throws NoContentException {
		RecipeHeader recipeHeaderToDelete = recipeRepository.findById(recipeId).orElseThrow(
				NoContentException::new);
		recipeRepository.delete(recipeHeaderToDelete);
	}
}
