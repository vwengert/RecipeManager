package com.recipemanager.repository;

import com.recipemanager.model.RecipeHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to persist Recipes
 */
@Repository
public interface RecipeHeaderRepository extends JpaRepository<RecipeHeader, Long> {
	Optional<RecipeHeader> findByName(String name);

	boolean existsByName(String name);
}
