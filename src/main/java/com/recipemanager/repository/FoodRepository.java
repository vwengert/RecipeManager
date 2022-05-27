package com.recipemanager.repository;

import com.recipemanager.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to persist Food
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
	Optional<Food> findByName(String name);
}
