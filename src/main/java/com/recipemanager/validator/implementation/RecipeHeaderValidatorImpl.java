package com.recipemanager.validator.implementation;

import com.recipemanager.model.RecipeHeader;
import com.recipemanager.repository.RecipeHeaderRepository;
import com.recipemanager.util.exceptions.NotFoundException;
import com.recipemanager.validator.RecipeHeaderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeHeaderValidatorImpl implements RecipeHeaderValidator {
	private final RecipeHeaderRepository recipeHeaderRepository;

	@Override
	public void checkRecipeHeaderExistsOrElseThrowException(RecipeHeader recipeHeader) throws NotFoundException {
		if (!recipeHeaderRepository.existsById(recipeHeader.getId())) throw new NotFoundException();
	}

}
