package com.recipemanager.repository;

import com.recipemanager.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to persist Recipes
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
