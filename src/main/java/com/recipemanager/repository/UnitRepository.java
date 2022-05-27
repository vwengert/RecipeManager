package com.recipemanager.repository;

import com.recipemanager.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to persist Units
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
	Optional<Unit> findByName(String name);

	Boolean existsByName(String name);
}
