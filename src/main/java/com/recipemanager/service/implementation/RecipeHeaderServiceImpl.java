package com.recipemanager.service.implementation;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.service.RecipeHeaderService;
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
	private final RecipeHeaderRepository recipeHeaderRepository;

	@Override
	public RecipeHeader getRecipeHeaderById(Long recipeId) throws NotFoundException {
		return recipeHeaderRepository.findById(recipeId).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public List<RecipeHeader> getRecipeHeader() {
		return recipeHeaderRepository.findAll();
	}

	@Override
	public RecipeHeader getRecipeHeaderByName(String recipeName) throws NotFoundException {
		return recipeHeaderRepository.findByName(recipeName).orElseThrow(
				NotFoundException::new);
	}

	@Override
	public RecipeHeader postRecipeHeader(RecipeHeader recipeHeader) throws IdNotAllowedException, FoundException {
		if (recipeHeader.getId() != null)
			throw new IdNotAllowedException();
		if (recipeHeaderRepository.existsByName(recipeHeader.getName()))
			throw new FoundException();
		return recipeHeaderRepository.save(recipeHeader);
	}

	@Override
	public RecipeHeader putRecipeHeader(RecipeHeader recipeHeader) throws NotFoundException {
		RecipeHeader oldRecipeHeader = recipeHeaderRepository.findById(recipeHeader.getId()).orElseThrow(
				NotFoundException::new
		);
		oldRecipeHeader.setName(recipeHeader.getName());
		oldRecipeHeader.setDescription(recipeHeader.getDescription());
		oldRecipeHeader.setPortions(recipeHeader.getPortions());

		return recipeHeaderRepository.save(oldRecipeHeader);
	}

	@Override
	public void deleteRecipeHeader(Long recipeId) throws NoContentException {
		RecipeHeader recipeHeaderToDelete = recipeHeaderRepository.findById(recipeId).orElseThrow(
				NoContentException::new);
		recipeHeaderRepository.delete(recipeHeaderToDelete);
	}
}
