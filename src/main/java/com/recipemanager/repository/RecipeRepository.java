package com.recipemanager.repository;

import com.recipemanager.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to persist Recipes
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> findByRecipeHeaderId(Long recipeHeaderId);
}
